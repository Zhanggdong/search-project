package com.huasisoft.search.demo.document;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 10:50
 * @Description 通过查询条件删除
 * @Version 2.0.0
 */
public class DeleteByQuery extends BaseTest{

    @Test
    @Ignore
    public void testForDeleteByQuery() throws Exception{
        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(transportClient)
                //查询条件
                .filter(QueryBuilders.matchQuery("id",1))
                // 索引名称
                .source("twitter")
                .get();
        long deleted = response.getDeleted(); //删除文档的数量
        if (deleted>0){
            System.out.println("删除成功!");
        }else{
            System.out.println("删除失败!");
        }
    }

    /**
     * 如果删除的比较多，需要执行的时间比较长，建议使用异步的删除操作
     * @throws Exception
     */
    @Test
    @Ignore
    public void testForAsyncDeleteByQuery() throws Exception{
       DeleteByQueryAction.INSTANCE
                .newRequestBuilder(transportClient)
                //查询条件
                .filter(QueryBuilders.matchQuery("id",1))
                // 索引名称
                .source("twitter")
                .execute(new ActionListener<BulkByScrollResponse>() {
                    @Override
                    public void onResponse(BulkByScrollResponse response) {
                        long deleted = response.getDeleted(); //删除文档的数
                        System.out.println("异步删除成功！" + deleted);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        // Handle the exception
                        System.out.println("异步删除失败:" + e.getMessage());
                    }
                });

    }
}
