package com.huasisoft.search.demo.search;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 14:42
 * @Description 搜索查询，返回查询匹配的结果，搜索一个index / type 或者多个index / type
 * @Version 2.0.0
 */
public class SearchAPI extends BaseTest {

    @Test
    public void testPrepareSearch() throws Exception{
        SearchResponse response = transportClient.prepareSearch("twitter")
                .setTypes("tweet")//可以是多个类型
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("user", "kimchy"))                 // Query 查询条件
//                .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter 过滤
                .setFrom(0).setSize(60).setExplain(true)
                .get();
        println(response);
    }

}
