package com.huasisoft.search.demo.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.common.util.Base64Util;
import com.huasisoft.search.demo.model.AttachmentTest;
import com.huasisoft.search.demo.service.AttachmentTestService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-25.
 * @Time 15:24
 * @Description 附件测试实现类
 * @Version 2.0.0
 */
@Service
public class AttachmentTestServiceImpl implements AttachmentTestService {
    private static final Logger LOG = LoggerFactory.getLogger(AttachmentTestServiceImpl.class);
    private static final String DATE_DAY_FORMAT="yyyy-MM-dd";
    private static final String DATE_SS_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    private TransportClient transportClient;
    private static final String INDEX_NAME = "office";
    private static final String TYPE_NAME = "attachment";

    @Override
    public void create(AttachmentTest attachmentTest) throws Exception {
        createBulk(Arrays.asList(attachmentTest));
    }

    @Override
    public void createBulk(List<AttachmentTest> attachmentTests) throws Exception {
        SimpleDateFormat dateDayFormat =  new SimpleDateFormat(DATE_DAY_FORMAT);
        SimpleDateFormat dateSsFormat =  new SimpleDateFormat(DATE_SS_FORMAT);
        BulkRequestBuilder request = transportClient.prepareBulk();
        for (AttachmentTest attachmentTest : attachmentTests) {
            String data = Base64Util.encodeFile(attachmentTest.getUrl());
            request.add(transportClient.prepareIndex(INDEX_NAME, TYPE_NAME, attachmentTest.getID())
                    // 设置附件通道
                    .setPipeline("single_attachment")
                    .setSource(jsonBuilder()
                            .startObject()
                            // TODO 可以利用反射来做
                            .field("id", attachmentTest.getID())
                            .field("guid", attachmentTest.getGUID())
                            .field("title", attachmentTest.getTitle())
                            .field("bwtype", attachmentTest.getBwtype())
                            .field("banwenbianhao", attachmentTest.getBanwenbianhao())
                            .field("laiwendanwei", attachmentTest.getLaiwendanwei())
                            .field("createdate", attachmentTest.getCreatedate())
                            .field("createdateTime", attachmentTest.getCreatedateTime())
                            .field("creatorGUID", attachmentTest.getCreatedate())
                            .field("creatorname", attachmentTest.getCreatorname())
                            .field("dn", attachmentTest.getDn())
                            // TODO 这里的方法仅供测试使用，在开发过程中需要重写方法来完
                            .field("data",data )
                            .field("departmentGUID", attachmentTest.getDepartmentGUID())
                            .field("type", attachmentTest.getType())
                            .endObject()
                    ));
            BulkResponse bulkResponse = request.execute().get();
            if (bulkResponse.hasFailures()) {
                // process failures by iterating through each bulk response item
                //处理失败
                LOG.info("添加附件索引失败");
            }
        }
    }

    @Override
    public List<AttachmentTest> search(String field, String keyword) {
        SearchResponse searchResponse = transportClient.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery(field, keyword))
                .setFrom(0)
                .setSize(10)
                .setExplain(true)
                .get();
        SearchHits hits = searchResponse.getHits();
        List<AttachmentTest> attachmentTests = new ArrayList<AttachmentTest>();
        for (SearchHit hit : hits) {
            AttachmentTest attachmentTest = JSONObject.parseObject(hit.getSourceAsString(), AttachmentTest.class);
            attachmentTests.add(attachmentTest);
        }
        return attachmentTests;
    }
}
