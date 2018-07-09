package com.huasisoft.search.demo.document;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-26.
 * @Time 10:25
 * @Description Index API
 * @Version 2.0.0
 */
public class IndexAPI extends BaseTest{
    /**
     * 使用JSON来构造索引
     */
    @Test
    @Ignore
    public void testUseForJson(){
        String json = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        transportClient.prepareIndex("twitter","tweet","2")
                .setSource(json, XContentType.JSON)
                .get();
        System.out.println("testUseForJson twitter 创建成功");
    }

    /**
     * 使用Map来构造索引
     * @throws Exception
     */
    @Test
    @Ignore
    public void testForUseMap() throws Exception {
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("user","kimchy");
        json.put("postDate",new Date());
        json.put("message","trying out Elasticsearch");
        IndexResponse indexResponse=transportClient.prepareIndex("twitter","tweet","3")
                .setSource(json)
                .get();
        System.out.println(indexResponse.getResult());

//        Assert.assertEquals("CREATED", response.getResult().name());

        System.out.println("testForUseMap twitter 创建成功");
    }

    /**
     * 使用elasticsearch官方提供的json构造器来构造文档内容
     * @throws Exception
     */
    @Test
    @Ignore
    public void testForUseXContentBuilder() throws Exception {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "kimchy")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject();
        transportClient.prepareIndex("twitter","tweet","1")
                .setSource(builder)
                .get();
        System.out.println("testForUseXContentBuilder twitter 创建成功");

    }
}
