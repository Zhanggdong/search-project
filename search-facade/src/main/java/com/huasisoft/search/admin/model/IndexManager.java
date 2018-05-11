package com.huasisoft.search.admin.model;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-11.
 * @Time 11:34
 * @Description TODO
 * @Version 2.0.0
 */
public class IndexManager {
    // 使用哪个Service执行
    private String beanName;
    // 索引类型
    private Integer type;
    /**
     * 是否增量执行：默认是增量执行
     */
    private boolean increment;

    /**
     * 索引名称
     */
    private String indexName;
    /**
     * 默认索引名称
     */
    private static final String DEFAULT_INDEXNAME = "office";
    /**
     * 索引类型
     */
    private String typeName;
    /**
     * 索引定义
     */
    private String mapping;


}
