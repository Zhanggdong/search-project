package com.huasisoft.search.admin.service.proxy;

import com.huasisoft.search.admin.model.IndexManagerBeans;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 15:02
 * @Description 索引管理代理接口：索引类型有很多种实现，公文、正文、意见、附件等
 * 每种类型的索引有自己独自的特点，该代理类主要是用来代理执行各个索引实现类的
 * @Version 2.0.0
 */
public interface IndexManagerProxyService{
    /**
     * 判断索引是否存在
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean existsIndexType(IndexManagerBeans beans);
    /**
     * 创建索引
     * @param indexName 索引名称
     * @param shards 分片数量，建议根据实际的环境来设置
     * @param replicas 副本数量
     * @return boolean
     */
    public boolean createIndex(IndexManagerBeans beans);

    /**
     * 删除索引数据
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean deleteIndex(IndexManagerBeans beans);

    /**
     * 删除索引中摸个类型的数据
     * @param type 索引类型，相当于某张表
     * @return boolean
     */
    public boolean deleteIndexType(IndexManagerBeans beans);

    /**
     * 设置mapping
     * @param indexName 索引名称
     * @param typeName 索引类型
     * @param mappings 索引定义信息
     * @return
     */
    public boolean setMapping(IndexManagerBeans beans) throws Exception;

    /**
     * 初始化索引数据
     * @return
     */
    public boolean initIndexData(IndexManagerBeans beans) throws Exception;
}
