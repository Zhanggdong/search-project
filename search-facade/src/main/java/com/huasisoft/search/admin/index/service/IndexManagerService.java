package com.huasisoft.search.admin.index.service;

import com.huasisoft.search.admin.index.beans.IndexManagerBeans;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 10:51
 * @Description 后台索引管理：用于从数据库中添加索引、定时执行索引策略
 * @Version 2.0.0
 */
public interface IndexManagerService<T>{
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

    /**
     * 索引任务状态停止<br/>
     * 构建索引过程中，后台管理员停止任务的操作接口
     * @param beans
     * @return
     * @throws Exception
     */
    public boolean stop(IndexManagerBeans beans ) throws Exception;

    /**
     * 技术任务数量
     * @return
     * @throws Exception
     */
    public int count() throws Exception;

    public int count(int from, int to) throws Exception;

    /**
     * 分段执行初始化任务，从from到to进行初始化
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    public boolean initIndexData(int from, int to,IndexManagerBeans beans) throws Exception;

}
