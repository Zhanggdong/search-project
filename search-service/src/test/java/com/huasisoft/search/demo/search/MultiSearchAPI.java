package com.huasisoft.search.demo.search;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 17:07
 * @Description 在同一API中执行多个搜索请求
 * @Version 2.0.0
 */
public class MultiSearchAPI extends BaseTest {

    @Test
    public void testMultiSearch(){
        SearchRequestBuilder builder1 = transportClient.prepareSearch()
                .setQuery(QueryBuilders.queryStringQuery("elasticsearch")).setSize(1);

        SearchRequestBuilder builder2 = transportClient.prepareSearch()
                .setQuery(QueryBuilders.matchQuery("name","kimchy"));

        MultiSearchResponse response = transportClient.prepareMultiSearch()
                .add(builder1)
                .add(builder2)
                .get();
        long nbHits = 0;
        for (MultiSearchResponse.Item item:response.getResponses()){
            SearchResponse searchResponse = item.getResponse();
            nbHits += searchResponse.getHits().getTotalHits();
            println(searchResponse);
        }

        System.out.println("nbHits:" + nbHits);
    }
}
