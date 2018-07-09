package com.huasisoft.search.demo.search;

import com.huasisoft.search.BaseTest;
import org.elasticsearch.action.search.ClearScrollRequestBuilder;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 15:50
 * @Description  Scroll API可以允许我们检索大量数据（甚至全部数据）
 * @Version 2.0.0
 */
public class ScrollsAPI extends BaseTest{
    private String scrollId;

    @Test
    @Ignore
    public void testScrolls() throws Exception {

        SearchResponse scrollResp = transportClient.prepareSearch("twitter")
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000)) //为了使用 scroll，初始搜索请求应该在查询中指定 scroll 参数，告诉 Elasticsearch 需要保持搜索的上下文环境多长时间（滚动时间）
                .setQuery(QueryBuilders.termQuery("user", "kimchy"))                 // Query 查询条件
                .setSize(5).get(); //max of 100 hits will be returned for each scroll
        //Scroll until no hits are returned

        scrollId = scrollResp.getScrollId();
        do {
            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...

                System.out.println("" + hit.getSource().toString());
            }

            scrollResp = transportClient.prepareSearchScroll(scrollId).setScroll(new TimeValue(60000)).execute().actionGet();
        }
        while (scrollResp.getHits().getHits().length != 0); // Zero hits mark the end of the scroll and the while loop.
    }

    @Override
    public void tearDown() throws Exception {
        ClearScrollRequestBuilder clearScrollRequestBuilder = transportClient.prepareClearScroll();
        clearScrollRequestBuilder.addScrollId(scrollId);
        ClearScrollResponse response = clearScrollRequestBuilder.get();

        if (response.isSucceeded()) {
            System.out.println("成功清除");
        }

        super.tearDown();
    }

}
