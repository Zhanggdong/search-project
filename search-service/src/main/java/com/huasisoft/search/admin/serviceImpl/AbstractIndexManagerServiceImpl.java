package com.huasisoft.search.admin.serviceImpl;

import com.huasisoft.search.admin.model.IndexManagerBeans;
import com.huasisoft.search.admin.service.IndexManagerService;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 8:27
 * @Description TODO
 * @Version 2.0.0
 */
public abstract class AbstractIndexManagerServiceImpl implements IndexManagerService{
    private static final Logger logger = LoggerFactory.getLogger(AbstractIndexManagerServiceImpl.class);

    @Autowired
    TransportClient client;

    @Override
    public boolean existsIndexType(String indexName) {
        IndicesExistsRequest request = new IndicesExistsRequest(indexName);
        IndicesExistsResponse response = getAdminClient().exists(request).actionGet();
        if (response.isExists()) {
            logger.info("索引{}已经存在!",indexName);
            return true;
        }else {
            logger.info("索引{}不存在!",indexName);
            return false;
        }
    }

    @Override
    public boolean createIndex(String indexName, int shards, int replicas) {
        Settings settings = Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
                .build();
        CreateIndexResponse createIndexResponse = getAdminClient()
                .prepareCreate(indexName.toLowerCase())
                .setSettings(settings)
                .execute().actionGet();

        boolean isIndexCreated = createIndexResponse.isAcknowledged();
        if (isIndexCreated) {
            logger.info("索引{}创建成功!",indexName);
        } else {
            logger.info("索引{}创建失败!",indexName);
        }
        return isIndexCreated;
    }

    @Override
    public boolean deleteIndex(String indexName) {
        DeleteIndexResponse deleteResponse = getAdminClient()
                .prepareDelete(indexName.toLowerCase())
                .execute()
                .actionGet();
        boolean isIndexDeleted = deleteResponse.isAcknowledged();
        if (isIndexDeleted) {
            logger.info("索引{}删除成功!",indexName);
        } else {
            logger.info("索引{}删除失败!",indexName);
        }
        return isIndexDeleted;
    }

    /**
     * 获取索引管理的IndicesAdminClient
     * @return
     */
    protected IndicesAdminClient getAdminClient() {
        return client.admin().indices();
    }

    @Override
    public boolean deleteIndexType(String indexName, String type) {

        return false;
    }

    @Override
    public boolean setMapping(String indexName, String typeName, String mappings) {
        PutMappingResponse response = getAdminClient().preparePutMapping(indexName)
                .setType(typeName)
                .setSource(mappings, XContentType.JSON)
                .get();
        boolean isMappingPut = response.isAcknowledged();
        if (isMappingPut) {
            logger.info("索引{}mappings成功!",indexName);
        } else {
            logger.info("索引{}mappings失败!",indexName);
        }
        return isMappingPut;
    }

    @Override
    public abstract boolean initIndexData(IndexManagerBeans beans) throws Exception;
}
