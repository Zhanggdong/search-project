package com.huasisoft.search.admin.index.serviceImpl;

import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.service.IndexManagerService;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 8:27
 * @Description 索引管理抽象类
 * @Version 2.0.0
 */
public abstract class AbstractIndexManagerServiceImpl implements IndexManagerService{
    private static final Logger logger = LoggerFactory.getLogger(AbstractIndexManagerServiceImpl.class);

    @Autowired
    TransportClient client;
    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Value("${db.file.path}")
    String eDrivePattern;
    @Value("${real.file.path}")
    String fDrivePattern;

    private boolean isBalance = false;

    //索引名称
    public String indexName;
    // 索引类型
    public String typeName;
    // 任务开始值
    private int from=0;
    // 任务结束值
    public int to=0;

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
        QueryBuilder query = QueryBuilders.matchQuery("match_all","{}");
        //client.delete(query)
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

    /**
     * 初始化索引数据的步骤基本上是固定的：
     * 1.初始化：检查索引是否存在，如果不存在，则定义索引相关信息
     * 2.计算需要写入的索引数量
     * 3.拆分任务
     * 4.提交线程池执行
     * 5.返回结果
     * @param beans
     * @return
     * @throws Exception
     */
    protected boolean process(IndexManagerBeans beans) throws Exception {
        long startDate = System.currentTimeMillis();
        // 初始化
        init(beans);
        // 计算需要写入的索引数量：
        int total = count(from,to);
        // 拆分任务
        if (total<=0){
            logger.info("没有查询到数据!");
            return true;
        }
        IndexManagerBeans.TaskBuilder builder = beans.builder(total);
        builder.SplitTask();

        // 提交线程池执行任务,返回执行结果
        CountDownLatch taskCount = new CountDownLatch(builder.getTaskCount());
        boolean success = doExecute(builder,taskCount,from,to);
        taskCount.await();
        long endDate = System.currentTimeMillis();
        logger.info("任务执行时间:{}毫秒",(endDate-startDate));
        return success;
    }

    public void init(IndexManagerBeans beans) throws IOException {
        this.indexName = beans.getIndexName();
        this.typeName = beans.getTypeName();
        // 判断索引是否存在：
        boolean isIndexExists = existsIndexType(indexName);
        if (!isIndexExists){
            setMapping();
        }
    }

    //public abstract boolean doExecute(IndexManagerBeans.TaskBuilder builder,CountDownLatch taskCount) throws InterruptedException;

    public abstract boolean doExecute(IndexManagerBeans.TaskBuilder builder,CountDownLatch taskCount,int from,int to) throws InterruptedException;

    public abstract int doCount() throws Exception;

    public abstract boolean setMapping() throws IOException;

    @Override
    public boolean initIndexData(IndexManagerBeans beans) throws Exception {
        boolean success = process(beans);
        return success;
    }

    public abstract boolean stop(IndexManagerBeans beans) throws Exception;

    @Override
    public int count() throws Exception {
        return doCount();
    }
    @Override
    public int count(int from, int to) throws Exception {
        int count = 0;
        if (isBalance){
            count = to-from;
        }else {
            count = count();
        }
        return count;
    }

    @Override
    public boolean initIndexData(int from, int to,IndexManagerBeans beans) throws Exception {
        isBalance = true;
        this.from = from;
        this.to = to;
        boolean success = process(beans);
        return success;
    }

    /**
     * 将数据库中含有F盘文件路径替换为E盘路径
     * @param url
     * @return
     */
    public String replaceConfigUrl(String url) {
        Pattern e_pattern = Pattern.compile(fDrivePattern);
        Matcher matcher = e_pattern.matcher(url);
        return matcher.replaceAll(eDrivePattern);
    }

    public int getPageNum(IndexManagerBeans.TaskBuilder builder, int from) {
        return from/builder.getPerCount()==0?from/builder.getPerCount():(from/builder.getPerCount()+1);
    }
}
