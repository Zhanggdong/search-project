package com.huasisoft.search.admin.service;

import com.huasisoft.search.admin.model.IndexManagerBeans;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 10:51
 * @Description 后台索引管理：用于从数据库中添加索引、定时执行索引策略
 * @Version 2.0.0
 */
public interface IndexManagerService{
    /**
     * 判断索引是否存在
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean existsIndexType(String indexName);
    /**
     * 创建索引
     * @param indexName 索引名称
     * @param shards 分片数量，建议根据实际的环境来设置
     * @param replicas 副本数量
     * @return boolean
     */
    public boolean createIndex(String indexName, int shards, int replicas);

    /**
     * 删除索引数据
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean deleteIndex(String indexName);

    /**
     * 删除索引中摸个类型的数据
     * @param type 索引类型，相当于某张表
     * @return boolean
     */
    public boolean deleteIndexType(String indexName, String type);

    /**
     * 设置mapping
     * @param indexName 索引名称
     * @param typeName 索引类型
     * @param mappings 索引定义信息
     * @return
     */
    public boolean setMapping(String indexName, String typeName, String mappings);

    /**
     * 初始化索引数据
     * @return
     */
    public boolean initIndexData(IndexManagerBeans beans ) throws Exception;

}
