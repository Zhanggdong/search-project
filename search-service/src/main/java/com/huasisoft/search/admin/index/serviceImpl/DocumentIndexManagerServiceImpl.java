package com.huasisoft.search.admin.index.serviceImpl;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.beans.IndexTaskSwitch;
import com.huasisoft.search.admin.index.model.ESBaseData;
import com.huasisoft.search.common.util.Base64Util;
import com.huasisoft.search.admin.index.model.DocumentBase;
import com.huasisoft.search.admin.index.service.DocumentBaseService;
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
 * @Time 14:13
 * @Description 公文正文索引管理接口实现
 * @Version 2.0.0
 */
@Service("documentIndexManagerService")
public class DocumentIndexManagerServiceImpl extends AbstractIndexManagerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(DocumentIndexManagerServiceImpl.class);
    private static final String FORMAT_DATE =  "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private DocumentBaseService documentBaseService;

    // 阻塞队列，用来存放从数据库中拉取的数据，从中获取数据写入ES
    private BlockingQueue<List<DocumentBase>> queue;

    @Value("${doc.threadPool.consumer.size}")
    private String consumerThreadSize;
    // 默认的消费端线程数量
    private static final int DEFAULT_CONSUMER_THREAD_SIZE = 5;

    @Override
    public boolean doExecute(IndexManagerBeans.TaskBuilder builder, CountDownLatch taskCountDownLatch,int from,int to) {
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
        // 这里需要计算任务偏移量
        for (;pageNum<=(beforCountTask+builder.getTaskCount());pageNum++){
            int start = (pageNum-1)*builder.getPerCount()+1;
            int end = pageNum*builder.getPerCount();
            threadPoolTaskExecutor.execute(new Producer(start,end));
        }
        return true;
    }


    @Override
    public int doCount() throws Exception {
        return documentBaseService.count();
    }

    @Override
    public boolean setMapping() throws IOException {
        return false;
    }

    @Override
    public boolean stop(IndexManagerBeans beans) throws Exception {
        IndexTaskSwitch.getInstance().setDocumentTaskSwitch(false);
        return !IndexTaskSwitch.getInstance().getDocumentTaskSwitch().get();
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
                if (IndexTaskSwitch.getInstance().getDocumentTaskSwitch().get()) {
                    logger.info("线程{}开始拉取正文索引数据", Thread.currentThread().getName());
                    List<DocumentBase> documents = documentBaseService.selectByPageFromTo(from, to);
                    queue.put(documents);
                    logger.info("线程{}添加正文索引数据到队列完成", Thread.currentThread().getName());
                }else {
                    logger.info("线程{}拉取正文索引数据任务中止",Thread.currentThread().getName());
                }
            } catch (SQLException e){
                logger.info("线程{}查询正文索引数据失败",Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Producer(from,to));
            }catch (Exception e) {
                // 抛出异常，也是再次将任务提交到线程池
                logger.info("线程{}写入正文索引数据到队列失败",Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Producer(from,to));
            }
        }

    }

    protected class Consumer implements Runnable{
        private BlockingQueue<List<DocumentBase>> queue;
        private CountDownLatch taskCountDownLatch;

        public Consumer(BlockingQueue<List<DocumentBase>> queue,CountDownLatch taskCountDownLatch) {
            this.queue = queue;
            this.taskCountDownLatch = taskCountDownLatch;
        }

        @Override
        public void run() {
            try {
                if (IndexTaskSwitch.getInstance().getDocumentTaskSwitch().get()){
                    while (true) {
                        logger.info("线程{}开始开始获取队列中正文索引数据", Thread.currentThread().getName());
                        List<DocumentBase> documents = this.queue.take();
                        List<ESBaseData> indexs = wrap(documents);
                        BulkRequestBuilder request = createBulkRequestBuilder(indexs);
                        BulkResponse bulkResponse = request.execute().get();
                        if (bulkResponse.hasFailures()) {
                            // process failures by iterating through each bulk response item
                            logger.info("线程{}添加正文索引数据失败,请检查数据格式是否正确!", Thread.currentThread().getName());
                        }
                        logger.info("线程{}构建正文索引数据完成", Thread.currentThread().getName());
                        this.taskCountDownLatch.countDown();
                    }
                }else {
                    logger.info("线程{}构建正文索引数据任务中止", Thread.currentThread().getName());
                    this.taskCountDownLatch.countDown();
                }
            } catch (Exception e) {
                // 抛出异常，也是再次将任务提交到线程池
                logger.info("线程{}构建正文索引数据任务异常", Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Consumer(queue,taskCountDownLatch));
            }
        }

    }

    private BulkRequestBuilder createBulkRequestBuilder(List<ESBaseData> indexs) throws JsonProcessingException {
        BulkRequestBuilder request = client.prepareBulk();
        for (ESBaseData index :indexs){
            ObjectMapper mapper = new ObjectMapper();
            // 使用序列化方式
            byte[] json = mapper.writeValueAsBytes(index);
            request.add(client.prepareIndex(indexName,typeName,index.getId())
                    // 添加附件文件写入管道
                    .setPipeline("single_document")
                    .setSource(json, XContentType.JSON));
        }
        return request;
    }

    private List<ESBaseData> wrap(List<DocumentBase> documents) throws Exception {
        // 使用fork/join来处理结果
        List<ESBaseData> indexs = new ArrayList<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        EncodeTask task = new EncodeTask(documents,0,documents.size());
        Future<List<ESBaseData>> future = forkJoinPool.submit(task);
        try {
            indexs = future.get();
        }catch (Exception e){
            logger.info("读取正文文件数据异常!");
            // 重试
            return wrap(documents);
        }
        return indexs;
    }

    protected class EncodeTask extends RecursiveTask<List<ESBaseData>>{
        private static final int THRESHOULD = 10;//任务阈值
        private List<DocumentBase> documents;
        private int start;
        private int end;
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);

        public EncodeTask(List<DocumentBase> documents, int start, int end) {
            this.documents = documents;
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
                for(DocumentBase document:documents){
                    String createtime = format.format(document.getCreatedatetime());
                    ESBaseData index = JSONObject.parseObject(JSONObject.toJSONString(document),ESBaseData.class);
                    index.setCreatedatetime(createtime);
                    if (StringUtils.isNoneBlank(replaceConfigUrl(document.getUrl()))){
                        // TODO 替换路径需要可配置
                        index.setData(Base64Util.getInstance().encodeFile(replaceConfigUrl(document.getUrl())));
                    }
                    indexs.add(index);
                }
            }else {
                // 继续分隔任务
                int middle = (end-start)/2;
                List<DocumentBase> leftDocuments = documents.subList(start,middle);
                EncodeTask leftTask = new EncodeTask(leftDocuments,start,leftDocuments.size());
                List<DocumentBase> rightDocuments = documents.subList(middle,end);
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
