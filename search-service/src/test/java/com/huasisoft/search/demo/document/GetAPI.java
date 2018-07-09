package com.huasisoft.search.demo.document;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.get.GetResponse;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-26.
 * @Time 10:48
 * @Description Get API
 * @Version 2.0.0
 */
public class GetAPI extends BaseTest{
    /**
     * 根据id查看文档
     */
    @Test
    @Ignore
    public void testForGetApi() throws Exception {
        /**
         * 根据id查看文档
         */
        GetResponse response = transportClient.prepareGet("twitter", "tweet", "2").get();

//        GetResponse response = client.prepareGet("twitter", "tweet", "1")
//                .setOperationThreaded(false)  //`true` 是在不同的线程里执行此次操作
//                .get();
        if (response.isExists()) {
            System.out.println("GetApi 有此文档：" + response.toString());
        } else {
            System.out.println("GetApi 没有此文档：" + response.toString());
        }
    }
}
