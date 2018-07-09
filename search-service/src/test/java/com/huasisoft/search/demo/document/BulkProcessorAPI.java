package com.huasisoft.search.demo.document;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 11:36
 * @Description BulkProcessorAPI 提供了一个简单的接口，在给定的大小数量上定时批量自动请求
 * @Version 2.0.0
 */
public class BulkProcessorAPI extends BaseTest{
    BulkProcessor bulkProcessor;

    @Test
    @Ignore
    public void testBulkProcessor() throws Exception{
        bulkProcessor = BulkProcessor.builder(transportClient,//增加elasticsearch客户端
                new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                //调用bulk之前执行 ，例如你可以通过request.numberOfActions()方法知道numberOfActions
                System.out.println("executionId = [" + executionId + "], request = [" + request + "]");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                //调用bulk之后执行 ，例如你可以通过request.hasFailures()方法知道是否执行失败
                System.out.println("executionId = [" + executionId + "], request = [" + request + "], response = [" + response + "]");
            }

            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable throwable) {
                //调用失败抛 Throwable
                System.out.println("executionId = [" + executionId + "], request = [" + request + "], failure = [" + throwable + "]");
            }}).setBulkActions(1000) //每次10000请求
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB)) //拆成5mb一块
                .setFlushInterval(TimeValue.timeValueSeconds(5)) //无论请求数量多少，每5秒钟请求一次。
                .setConcurrentRequests(1) //设置并发请求的数量。值为0意味着只允许执行一个请求。值为1意味着允许1并发请求。
                //设置自定义重复请求机制，最开始等待100毫秒，之后成倍更加，重试3次，当一次或多次重复请求失败后因为计算资源
                // 不够抛出 EsRejectedExecutionException 异常，可以通过BackoffPolicy.noBackoff()方法关闭重试机制
                .setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
                .build();
        // 增加任务
        bulkProcessor.add(new IndexRequest("twitter","tweet","5")
                .source(jsonBuilder()
                        .startObject()
                        .field("user", "mic jodern")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                ));

    }

    @Override
    public void tearDown() throws Exception {
        bulkProcessor.awaitClose(10, TimeUnit.MINUTES);
        super.tearDown();
    }
}
