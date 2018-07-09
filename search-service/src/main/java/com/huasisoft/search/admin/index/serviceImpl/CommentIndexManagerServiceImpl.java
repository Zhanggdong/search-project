package com.huasisoft.search.admin.index.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.beans.IndexTaskSwitch;
import com.huasisoft.search.admin.index.model.ESBaseData;
import com.huasisoft.search.admin.index.model.WorkflowCommentBase;
import com.huasisoft.search.admin.index.service.WorkflowCommentBaseService;
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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 14:14
 * @Description 公文意见索引管理接口实现
 * @Version 2.0.0
 */
@Service("commentIndexManagerService")
public class CommentIndexManagerServiceImpl extends AbstractIndexManagerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(CommentIndexManagerServiceImpl.class);
    private static final String FORMAT_DATE =  "yyyy-MM-dd HH:mm:ss";

    private AtomicBoolean taskSwitch = new AtomicBoolean(true);

    private BlockingQueue<List<WorkflowCommentBase>> queue;

    @Value("${comment.threadPool.consumer.size}")
    private String consumerThreadSize;
    // 默认的消费端线程数量
    private static final int DEFAULT_CONSUMER_THREAD_SIZE = 3;

    @Autowired
    private WorkflowCommentBaseService commentBaseService;

    @Override
    public boolean doExecute(IndexManagerBeans.TaskBuilder builder,CountDownLatch taskCountDownLatch,int from,int to) {
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
        return commentBaseService.count();
    }

    @Override
    public boolean setMapping() throws IOException {
        return false;
    }

    @Override
    public boolean stop(IndexManagerBeans beans) throws Exception {
        IndexTaskSwitch.getInstance().setCommnetTaskSwitch(false);
        return !IndexTaskSwitch.getInstance().getCommnetTaskSwitch().get();
    }

    /**
     * 分页查询Task
     */
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
                if (taskSwitch.get()){
                    logger.info("线程{}开始拉取意见索引数据",Thread.currentThread().getName());
                    long startDateTime = System.currentTimeMillis();
                    List<WorkflowCommentBase> comments = commentBaseService.selectByPageFromTo(from, to);
                    long endDateTime = System.currentTimeMillis();
                    logger.info("线程{}开始拉取意见索引数据用时:{}毫秒",Thread.currentThread().getName(),endDateTime-startDateTime);
                    queue.put(comments);
                    logger.info("线程{}获取意见数据写入队列完成",Thread.currentThread().getName());
                }else {
                    logger.info("线程{}拉取意见数据任务中止",Thread.currentThread().getName());
                }
            }catch (SQLException e){
                logger.info("线程{}查询意见数据失败",Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Producer(from, to));
            }
            catch (Exception e){
                logger.info("线程{}意见数据写入队列失败",Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Producer(from, to));
            }
        }
    }

    /**
     * 分页查询Task
     */
    protected class Consumer implements Runnable {
        private BlockingQueue<List<WorkflowCommentBase>> queue;
        private CountDownLatch taskCountDownLatch;
        public Consumer(BlockingQueue<List<WorkflowCommentBase>> queue,CountDownLatch taskCountDownLatch) {
            this.queue = queue;
            this.taskCountDownLatch = taskCountDownLatch;
        }

        @Override
        public void run() {
            try {
                if (taskSwitch.get()){
                    while (true) {
                        logger.info("线程{}获取队列中意见数据", Thread.currentThread().getName());
                        List<WorkflowCommentBase> comments = queue.take();
                        List<ESBaseData> indexs = wrap(comments);
                        BulkRequestBuilder request = createBulkRequestBuilder(indexs);
                        BulkResponse bulkResponse = request.execute().get();
                        if (bulkResponse.hasFailures()) {
                            // process failures by iterating through each bulk response item
                            //处理失败
                            logger.info("线程{}写入意见索引失败，数据格式错误!", Thread.currentThread().getName());
                        }
                        logger.info("线程{}构建意见索引数据完成", Thread.currentThread().getName());
                        this.taskCountDownLatch.countDown();
                    }
                }else {
                    logger.info("线程{}构建意见索引数据任务中止", Thread.currentThread().getName());
                    this.taskCountDownLatch.countDown();
                }
            } catch (Exception e) {
                logger.info("线程{}构建意见索引数据任务异常", Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Consumer(queue,taskCountDownLatch));
            }
        }
    }

    /**
     * 添加数据到BulkRequestBuilder
     * @param indexs 公文索引数据
     * @return
     * @throws JsonProcessingException
     */
    private BulkRequestBuilder createBulkRequestBuilder(List<ESBaseData> indexs) throws JsonProcessingException {
        BulkRequestBuilder request = client.prepareBulk();
        for (ESBaseData index :indexs){
            ObjectMapper mapper = new ObjectMapper();
            // 使用序列化方式
            byte[] json = mapper.writeValueAsBytes(index);
            request.add(client.prepareIndex(indexName,typeName,index.getId())
                    .setSource(json, XContentType.JSON));
        }
        return request;
    }
    /**
     * 将查询到的数据库原始数据构造为ES全文检索数据
     * @param comments OA意见数据
     * @return
     * @throws Exception
     */
    private List<ESBaseData> wrap(List<WorkflowCommentBase> comments) throws Exception {
        List<ESBaseData> indexs = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        for (WorkflowCommentBase comment:comments){
            String createtime = format.format(comment.getCreatedatetime());
            ESBaseData index = JSONObject.parseObject(JSONObject.toJSONString(comment),ESBaseData.class);
            index.setCreatedatetime(createtime);
            index.setData(comment.getContent());
            indexs.add(index);
        }
        return indexs;
    }
}
