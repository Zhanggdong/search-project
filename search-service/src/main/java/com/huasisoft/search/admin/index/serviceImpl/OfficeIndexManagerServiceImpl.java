package com.huasisoft.search.admin.index.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.beans.IndexTaskSwitch;
import com.huasisoft.search.admin.index.model.ESBaseData;
import com.huasisoft.search.admin.index.model.OfficeBase;
import com.huasisoft.search.admin.index.service.OfficeBaseService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
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

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 9:05
 * @Description 公文基本信息索引管理接口实现
 * @Version 2.0.0
 */
@Service("officeIndexManagerService")
public class OfficeIndexManagerServiceImpl extends AbstractIndexManagerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(OfficeIndexManagerServiceImpl.class);
    private static final String FORMAT_DATE =  "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private OfficeBaseService officeBaseService;

    // 阻塞队列，用来存放从数据库中拉取的数据，从中获取数据写入ES
    private BlockingQueue<List<OfficeBase>> queue;

    @Value("${office.threadPool.consumer.size}")
    private String consumerThreadSize;
    // 默认的消费端线程数量
    private static final int DEFAULT_CONSUMER_THREAD_SIZE = 2;

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
        int total = officeBaseService.count();
        return total;
    }

    @Override
    public boolean setMapping() throws IOException {
        XContentBuilder builder = null;
        try {
            builder = jsonBuilder()
                    .startObject()
                    .startObject("properties")
                    .startObject("ID")
                    .field("type","keyword")
                    .endObject()
                    .startObject("GUID")
                    .field("type","keyword")
                    .endObject()
                    .startObject("title")
                    .field("type","text")
                    .field("store",true)
                    .field("analyzer","ik_max_word")
                    .field("search_analyzer","ik_max_word")
                    .field( "copy_to","search")
                    .endObject()
                    .startObject("bwtype")
                    .field("type","integer")
                    .endObject()
                    .startObject("banwenbianhao")
                    .field("type","keyword")
                    .field("store","true")
                    .field( "copy_to","search")
                    .endObject()
                    .startObject("laiwendanwei")
                    .field("type","text")
                    .field("store",true)
                    .field("analyzer","ik_max_word")
                    .field("search_analyzer","ik_max_word")
                    .field( "copy_to","search")
                    .endObject()
                    .startObject("createdate")
                    .field("type","date")
                    .field("format","yyyy-MM-dd")
                    .field("store",true)
                    .endObject()
                    .startObject("createdatetime")
                    .field("type","date")
                    .field("format","yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
                    .field("store",true)
                    .endObject()
                    .startObject("creatorGUID")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("creatorname")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("dn")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("type")
                    .field("type","integer")
                    .field("store",true)
                    .endObject()
                    .startObject("bureauname")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("data")
                    .field("type","text")
                    .field("store",true)
                    .field("analyzer","ik_max_word")
                    .field("search_analyzer","ik_max_word")
                    .field( "copy_to","search")
                    .endObject()
                    .startObject("departments")
                    .field("type","nested")
                    .startObject("properties")
                    .startObject("workflowinstanceGUID")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("senduserGUID")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("sendusername")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("departmentname")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("departmentGUID")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .startObject("dn")
                    .field("type","keyword")
                    .field("store",true)
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(builder.toString());
        logger.info("Mapping{} is put to elasticsearch!",builder.toString());
        return setMapping(indexName, typeName, builder.toString());
    }

    @Override
    public boolean stop(IndexManagerBeans beans) throws Exception {
        IndexTaskSwitch.getInstance().setOfficeTaskSwitch(false);
        return !IndexTaskSwitch.getInstance().getOfficeTaskSwitch().get();
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
                if (IndexTaskSwitch.getInstance().getOfficeTaskSwitch().get()) {
                    logger.info("线程{}开始拉取公文基本信息索引数据", Thread.currentThread().getName());
                    List<OfficeBase> offices = officeBaseService.selectByPageFromTo(from, to);
                    queue.put(offices);
                    logger.info("线程{}添加公文基本信息数据到队列完成", Thread.currentThread().getName());
                }else {
                    logger.info("线程{}拉取公文基本信息数据任务中止",Thread.currentThread().getName());
                }
            } catch (SQLException e){
                logger.info("线程{}查询公文基本信息数据失败",Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Producer(from, to));
            }catch (Exception e) {
                // 抛出异常，也是再次将任务提交到线程池
                logger.info("线程{}写入公文基本信息数据到队列失败",Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Producer(from, to));
            }
        }

    }

    protected class Consumer implements Runnable{
        private BlockingQueue<List<OfficeBase>> queue;
        private CountDownLatch taskCountDownLatch;

        public Consumer(BlockingQueue<List<OfficeBase>> queue,CountDownLatch taskCountDownLatch) {
            this.queue = queue;
            this.taskCountDownLatch = taskCountDownLatch;
        }

        @Override
        public void run() {
            try {
                if (IndexTaskSwitch.getInstance().getOfficeTaskSwitch().get()){
                    while (true) {
                        logger.info("线程{}开始开始获取队列中公文基本信息索引数据", Thread.currentThread().getName());
                        List<OfficeBase>  offices= this.queue.take();
                        List<ESBaseData> indexs = wrap(offices);
                        BulkRequestBuilder request = createBulkRequestBuilder(indexs);
                        BulkResponse bulkResponse = request.execute().get();
                        if (bulkResponse.hasFailures()) {
                            // process failures by iterating through each bulk response item
                            logger.info("线程{}添加公文基本信息索引数据失败,请检查数据格式是否正确!", Thread.currentThread().getName());
                        }
                        logger.info("线程{}添加公文基本信息索引数据完成", Thread.currentThread().getName());
                        this.taskCountDownLatch.countDown();
                    }
                }else {
                    logger.info("线程{}添加公文基本信息索引数据任务中止", Thread.currentThread().getName());
                    this.taskCountDownLatch.countDown();
                }
            } catch (Exception e) {
                // 抛出异常，也是再次将任务提交到线程池
                logger.info("线程{}添加公文基本信息索引数据任务异常", Thread.currentThread().getName());
                threadPoolTaskExecutor.execute(new Consumer(queue,taskCountDownLatch));
            }
        }

    }

    /**
     * 将数据库对象转换为ES索引数据对象
     * @param offices
     * @return
     */
    private List<ESBaseData> wrap(List<OfficeBase> offices) {
        List<ESBaseData> indexs = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        for (OfficeBase office:offices){
            String createtime = format.format(office.getCreatedatetime());
            ESBaseData index = JSONObject.parseObject(JSONObject.toJSONString(office),ESBaseData.class);
            index.setCreatedatetime(createtime);
            indexs.add(index);
        }
        return indexs;
    }

    private BulkRequestBuilder createBulkRequestBuilder( List<ESBaseData> indexs) throws IOException {
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

}
