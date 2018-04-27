package com.huasisoft.search.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-27.
 * @Time 15:04
 * @Description TODO
 * @Version 2.0.0
 */
public class ESUtils {
    public static void println(SearchResponse searchResponse) {
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println(JSON.toJSONString(searchHit.getSource(), SerializerFeature.PrettyFormat));
        }
    }
}
