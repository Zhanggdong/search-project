package com.huasisoft.search.demo.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.demo.model.Logs;
import com.huasisoft.search.demo.service.LogsService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-23.
 * @Time 11:44
 * @Description ES操作demo实现类
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service
@Service("logsService")
public class LogsServiceImpl implements LogsService {

    @Autowired
    private TransportClient transportClient;
    private static final String INDEX = "logs";
    private static final String TYPE = "log";
    @Override
    public void createIndex(String index,String type) {
        IndexRequestBuilder request = transportClient.prepareIndex(index,type);
    }

    @Override
    public Logs create(Logs log) throws IOException {
        List<Logs> logs = new ArrayList<>();
        logs.add(log);
        createBatch(logs);
        return log;
    }

    @Override
    public List<Logs> createBatch(List<Logs> logs) throws IOException {
        int i=1;
        BulkRequestBuilder bulkRequest = transportClient.prepareBulk();
        for (Logs log : logs) {
            bulkRequest.add(transportClient.prepareIndex(INDEX, TYPE, i+"")
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("userId", log.getUserId())
                            .field("system", log.getSystem())
                            .field("url", log.getUrl())
                            .field("content", log.getContent())
                            .endObject()
                    )
            );
            i++;
        }
        bulkRequest.execute();
        return logs;
    }

    @Override
    public List<Logs> searchAll() {
        SearchResponse response = transportClient.prepareSearch(INDEX)
                .setTypes(TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery("content", "demo"))
                .setFrom(0).setSize(60).setExplain(true)
                .get();
        SearchHits hits = response.getHits();
        List<Logs> logs = new ArrayList<>();
        for (SearchHit searchHit:hits){
            Logs log = JSONObject.parseObject(searchHit.getSourceAsString(),Logs.class);
            logs.add(log);
        }
        return logs;
    }
}
