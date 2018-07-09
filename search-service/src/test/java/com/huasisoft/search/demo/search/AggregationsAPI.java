package com.huasisoft.search.demo.search;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 17:29
 * @Description 在搜索中添加聚合
 * 官方文档 @see <a href='https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-search-aggs.html'></a>
 * 中文文档 @see <a href='https://es.quanke.name/search-api/using-aggregations.html'></a>
 * @Version 2.0.0
 */
public class AggregationsAPI extends BaseTest{
    @Test
    @Ignore
    public void testSearchAggregations() throws Exception {
        SearchResponse response = transportClient.prepareSearch("twitter")
                .setQuery(QueryBuilders.matchAllQuery())
                .addAggregation(AggregationBuilders.terms("agg1").field("user"))
                .get();
        Terms agg1 = response.getAggregations().get("agg1");
        println(response);
        System.out.println(""+agg1.getName());
        System.out.println("agg1:" + agg1.getBuckets().toString());

        for (Terms.Bucket bucket:
                agg1.getBuckets()) {
            System.out.println(""+bucket.getDocCount());
        }


        //注意： 可能会报Fielddata is disabled on text fields by default
        //聚合这些操作用单独的数据结构(fielddata)缓存到内存里了，需要单独开启，官方解释在此fielddata
        //https://www.elastic.co/guide/en/elasticsearch/reference/current/fielddata.html
    }
}
