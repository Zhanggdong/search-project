package com.huasisoft.search.demo.document;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-26.
 * @Time 10:52
 * @Description Delete API
 * @Version 2.0.0
 */
public class DeleteAPI extends BaseTest{

    /**
     * 通过ID删除文档
     * @throws Exception
     */
    @Test
    @Ignore
    public void testForDeleteAPI() throws Exception{
        DeleteResponse response = transportClient.prepareDelete("twitter", "tweet", "1")
                .get();

        // 使用异步方式删除文档
        /*DeleteResponse response = transportClient.prepareDelete("twitter", "tweet", "1")
                .setOperationThreaded(false)
                .get();*/
        System.out.println("删除成功:"+response.toString());
    }
}
