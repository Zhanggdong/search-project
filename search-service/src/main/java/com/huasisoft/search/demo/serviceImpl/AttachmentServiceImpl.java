package com.huasisoft.search.demo.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.common.util.Base64Util;
import com.huasisoft.search.admin.index.model.AttachmentBase;
import com.huasisoft.search.demo.service.AttachmentService;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.stereotype.Service;

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
@com.alibaba.dubbo.config.annotation.Service
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {
    private static final Logger LOG = LoggerFactory.getLogger(AttachmentServiceImpl.class);
    private static final String DATE_DAY_FORMAT="yyyy-MM-dd";
    private static final String DATE_SS_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private TransportClient transportClient;
    private static final String INDEX_NAME = "office";
    private static final String TYPE_NAME = "attachment";

    @Override
    public boolean create(AttachmentBase attachmentTest) throws Exception {
        return createBulk(Arrays.asList(attachmentTest));
    }

    @Override
    public boolean createBulk(List<AttachmentBase> attachmentTests) throws Exception {
        SimpleDateFormat dateDayFormat =  new SimpleDateFormat(DATE_DAY_FORMAT);
        SimpleDateFormat dateSsFormat =  new SimpleDateFormat(DATE_SS_FORMAT);
        BulkRequestBuilder request = transportClient.prepareBulk();
        for (AttachmentBase attachmentTest : attachmentTests) {
            String createtime = dateSsFormat.format(attachmentTest.getCreatedatetime());
            String data = null;
            if (StringUtils.isNoneBlank(attachmentTest.getUrl())){
                data = Base64Util.getInstance().encodeFile(attachmentTest.getUrl());
            }
            request.add(transportClient.prepareIndex(INDEX_NAME, TYPE_NAME, attachmentTest.getId())
                    // 设置附件通道
                    .setPipeline("single_attachment")
                    .setSource(jsonBuilder()
                            .startObject()
                            // TODO 可以利用反射来做
                            .field("id", attachmentTest.getId())
                            .field("guid", attachmentTest.getGuid())
                            .field("title", attachmentTest.getTitle())
                            .field("bwtype", attachmentTest.getBwtype())
                            .field("banwenbianhao", attachmentTest.getBanwenbianhao())
                            .field("laiwendanwei", attachmentTest.getLaiwendanwei())
                            .field("createdate", attachmentTest.getCreatedate())
                            .field("createdateTime", createtime)
                            .field("creatorGUID", attachmentTest.getCreatedate())
                            .field("creatorname", attachmentTest.getCreatorname())
                            .field("dn", attachmentTest.getDn())
                            // TODO 这里的方法仅供测试使用，在开发过程中需要重写方法来完
                            .field("data",data )
                            .field("departments", attachmentTest.getDepartments())
                            .field("type", attachmentTest.getType())
                            .endObject()
                    ));
            BulkResponse bulkResponse = request.execute().get();
            if (bulkResponse.hasFailures()) {
                // process failures by iterating through each bulk response item
                //处理失败
                LOG.info("添加附件索引失败");
                return false;
            }
        }
        return true;
    }

    @Override
    public List<AttachmentBase> search(String field, String keyword) {
        SearchResponse searchResponse = transportClient.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchQuery(field, keyword))
                .setFrom(0)
                .setSize(10)
                .setExplain(true)
                .get();
        SearchHits hits = searchResponse.getHits();
        List<AttachmentBase> attachmentTests = new ArrayList<AttachmentBase>();
        for (SearchHit hit : hits) {
            AttachmentBase attachmentTest = JSONObject.parseObject(hit.getSourceAsString(), AttachmentBase.class);
            attachmentTests.add(attachmentTest);
        }
        return attachmentTests;
    }
}
