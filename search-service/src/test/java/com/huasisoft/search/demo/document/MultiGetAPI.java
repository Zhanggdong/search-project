package com.huasisoft.search.demo.document;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 11:19
 * @Description 一次获取多个文档 API
 * @Version 2.0.0
 */
public class MultiGetAPI extends BaseTest{

    @Test
    @Ignore
    public void testForPrepareMultiGet()  throws Exception{
        MultiGetResponse multiGetItemResponses = transportClient.prepareMultiGet()
                .add("twitter", "tweet", "1") //一个id的方式
                .add("twitter", "tweet", "2", "3", "4") //多个id的方式
                .add("another", "type", "foo")  //可以从另外一个索引获取
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) { //迭代返回值
            GetResponse response = itemResponse.getResponse();
            if (response != null && response.isExists()) {      //判断是否存在
                String json = response.getSourceAsString(); //_source 字段
                System.out.println("_source 字段:" + json);
            }
        }
    }

}
