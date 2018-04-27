package com.huasisoft.search.demo.document;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 11:34
 * @Description 批量插入 API
 * @Version 2.0.0
 */
public class BulkAPI extends BaseTest {
    @Test
    public void testPrepareBulk() throws IOException {
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
// either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(transportClient.prepareIndex("twitter", "tweet", "10")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
        );

        bulkRequest.add(transportClient.prepareIndex("twitter", "tweet", "11")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );

        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            //处理失败
            System.out.println("失败：" + bulkResponse.toString());
        }
    }
}
