package com.huasisoft.search.admin.index.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.beans.IndexTaskSwitch;
import com.huasisoft.search.admin.index.model.ESBaseData;
import com.huasisoft.search.common.util.Base64Util;
import com.huasisoft.search.admin.index.model.AttachmentBase;
import com.huasisoft.search.admin.index.service.AttachmentBaseService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 14:14
 * @Description 附件索引管理接口实现
 * @Version 2.0.0
 */
@Service("attachmentIndexManagerService")
public class AttachmentIndexManagerServiceImpl extends AbstractIndexManagerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentIndexManagerServiceImpl.class);
    private static final String FORMAT_DATE =  "yyyy-MM-dd HH:mm:ss";

    @Value("${attachment.threadPool.consumer.size}")
    private String consumerThreadSize;
    // 默认的消费端线程数量
    private static final int DEFAULT_CONSUMER_THREAD_SIZE = 10;

    @Autowired
    private AttachmentBaseService attachmentBaseService;
    // 阻塞队列，用来存放从数据库中拉取的数据，从中获取数据写入ES
    private BlockingQueue<List<AttachmentBase>> queue;

    @Override
    public boolean doExecute(IndexManagerBeans.TaskBuilder builder, CountDownLatch taskCountDownLatch,int from ,int to) {
        queue = new ArrayBlockingQueue<>(builder.getTaskCount());
        // 设置消费端线程数
        if (StringUtils.isBlank(consumerThreadSize)){
            consumerThreadSize = String.valueOf(DEFAULT_CONSUMER_THREAD_SIZE);
        }
        for (int i=1;i<=Integer.valueOf(consumerThreadSize);i++){
            threadPoolTaskExecutor.execute(new Consumer(queue,taskCountDownLatch));
        }
        // 计算起始值:
        int pageNum= 1;
        // from之前的任务数：
        int beforCountTask = 0;
        // 传入任务分段
        if (from!=0&&to!=0){
            pageNum = getPageNum(builder, from);
            beforCountTask = pageNum;
        }
        // 设置生产者线程数
        for (;pageNum<=(beforCountTask+builder.getTaskCount());pageNum++){
            int start = (pageNum-1)*builder.getPerCount()+1;
            int end = pageNum*builder.getPerCount();
            threadPoolTaskExecutor.execute(new Producer(start,end));
        }
        return true;
    }

    @Override
    public int doCount() throws Exception {
        return attachmentBaseService.count();
    }

    @Override
    public boolean stop(IndexManagerBeans beans) throws Exception {
        IndexTaskSwitch.getInstance().setAttachmentTaskSwitch(false);
        return !IndexTaskSwitch.getInstance().getAttachmentTaskSwitch().get();
    }

    @Override
    public boolean setMapping() throws IOException {
        return false;
    }

    protected class Producer implements Runnable{
        private int from;
        private int to;
        public Producer(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            try {
                if (IndexTaskSwitch.getInstance().getAttachmentTaskSwitch().get()) {
                    logger.info("线程{}开始构建附件索引数据", Thread.currentThread().getName());
                    List<AttachmentBase> attachments = attachmentBaseService.selectByPageFromTo(from, to);
                    queue.put(attachments);
                    logger.info("线程{}构建附件索引数据完成", Thread.currentThread().getName());
                }else {
                    logger.info("线程{}拉取附件数据任务中止",Thread.currentThread().getName());
                }
            } catch (SQLException e){
                logger.info("线程{}查询附件数据失败",Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Producer(from, to));
            }catch (Exception e) {
                e.printStackTrace();
                // 抛出异常，也是再次将任务提交到线程池
                logger.info("线程{}附件数据写入队列失败",Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Producer(from, to));
            }
        }

    }

    protected class Consumer implements Runnable{
        private BlockingQueue<List<AttachmentBase>> queue;
        private CountDownLatch taskCountDownLatch;

        public Consumer(BlockingQueue<List<AttachmentBase>> queue,CountDownLatch taskCountDownLatch) {
            this.queue = queue;
            this.taskCountDownLatch = taskCountDownLatch;
        }

        @Override
        public void run() {
            try {
                if (IndexTaskSwitch.getInstance().getAttachmentTaskSwitch().get()){
                    while (true) {
                        logger.info("线程{}开始开始获取队列中附件索引数据", Thread.currentThread().getName());
                        List<AttachmentBase> attachments = this.queue.take();
                        List<ESBaseData> indexs = wrap(attachments);
                        BulkRequestBuilder request = createBulkRequestBuilder(indexs);
                        BulkResponse bulkResponse = request.execute().get();
                        if (bulkResponse.hasFailures()) {
                            // process failures by iterating through each bulk response item
                            logger.info("线程{}附件索引添加失败,请检查数据格式是否正确!", Thread.currentThread().getName());
                        }
                        logger.info("线程{}构建附件索引数据完成", Thread.currentThread().getName());
                        this.taskCountDownLatch.countDown();
                    }
                }else {
                    logger.info("线程{}构建附件索引数据任务中止", Thread.currentThread().getName());
                    this.taskCountDownLatch.countDown();
                }
            } catch (Exception e) {
                // 抛出异常，也是再次将任务提交到线程池
                logger.info("线程{}构建附件索引数据任务异常", Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Consumer(queue,taskCountDownLatch));
            }
        }

    }

    private BulkRequestBuilder createBulkRequestBuilder(List<ESBaseData> indexs) throws IOException {
        BulkRequestBuilder request = client.prepareBulk();
        for (ESBaseData index :indexs){
            ObjectMapper mapper = new ObjectMapper();
            // 使用序列化方式
            byte[] json = mapper.writeValueAsBytes(index);
            request.add(client.prepareIndex(indexName,typeName,index.getId())
                    // 添加附件文件写入管道
                    .setPipeline("single_attachment")
                    .setSource(json, XContentType.JSON));
        }
        return request;
    }

    private List<ESBaseData> wrap(List<AttachmentBase> attachments) throws Exception {
        // 使用fork/join来处理结果
        List<ESBaseData> indexs = new ArrayList<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        EncodeTask task = new EncodeTask(attachments,0,attachments.size());
        Future<List<ESBaseData>> future = forkJoinPool.submit(task);
        try {
            indexs = future.get();
        }catch (Exception e){
            logger.info("读取正文文件数据异常!");
            // 重试
            return wrap(attachments);
        }
        return indexs;
    }

    /*private List<ESBaseData> wrap(List<AttachmentBase> attachments) throws Exception {
        List<ESBaseData> indexs = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        for (AttachmentBase attachment:attachments){
            String createtime = format.format(attachment.getCreatedatetime());
            ESBaseData index = JSONObject.parseObject(JSONObject.toJSONString(attachment),ESBaseData.class);
            index.setCreatedatetime(createtime);
            index.setTitle(attachment.getFjname());
            if (StringUtils.isNoneBlank(attachment.getUrl())){
                // 这一段的性能很有问题，会非常的缓慢
                index.setData(Base64Util.getInstance().encodeFile(replaceConfigUrl(attachment.getUrl())));
            }
            indexs.add(index);
        }
        return indexs;
    }*/

    protected class EncodeTask extends RecursiveTask<List<ESBaseData>> {
        private static final int THRESHOULD = 10;//任务阈值
        private List<AttachmentBase> attachments;
        private int start;
        private int end;
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

        public EncodeTask(List<AttachmentBase> attachments, int start, int end) {
            this.attachments = attachments;
            this.start = start;
            this.end = end;
        }

        @Override
        protected List<ESBaseData> compute() {
            List<ESBaseData> indexs = new ArrayList<>();
            // 判断任务,如果小月阈值，则提交任务
            boolean canEncode = (end-start)<=THRESHOULD;
            if (canEncode){
                //List<ESBaseData> subResult = new ArrayList<>();
                for(AttachmentBase attachment:attachments){
                    String createtime = format.format(attachment.getCreatedatetime());
                    ESBaseData index = JSONObject.parseObject(JSONObject.toJSONString(attachment),ESBaseData.class);
                    index.setCreatedatetime(createtime);
                    index.setTitle(attachment.getFjname());
                    if (StringUtils.isNoneBlank(replaceConfigUrl(attachment.getUrl()))){
                        // TODO 替换路径需要可配置
                        index.setData(Base64Util.getInstance().encodeFile(replaceConfigUrl(attachment.getUrl())));
                    }
                    indexs.add(index);
                }
            }else {
                // 继续分隔任务
                int middle = (end-start)/2;
                List<AttachmentBase> leftDocuments = attachments.subList(start,middle);
                EncodeTask leftTask = new EncodeTask(leftDocuments,start,leftDocuments.size());
                List<AttachmentBase> rightDocuments = attachments.subList(middle,end);
                EncodeTask rightTask = new EncodeTask(rightDocuments,start,rightDocuments.size());
                // 执行子任务
                leftTask.fork();
                rightTask.fork();
                // 获取结果
                List<ESBaseData> leftResult = leftTask.join();
                List<ESBaseData> rightResult = rightTask.join();
                // 合并结果集
                indexs.addAll(leftResult);
                indexs.addAll(rightResult);
            }
            return indexs;
        }
    }

}
