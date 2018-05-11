package com.huasisoft.search.admin.beans;

import com.huasisoft.search.admin.constant.DBIndexType;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-11.
 * @Time 11:59
 * @Description TODO
 * @Version 2.0.0
 */
public class IndexManagerRequest extends AbstractRequest implements Serializable{
    private static final long serialVersionUID = 4313764445024388933L;
    /**
     * 使用哪个Service执行
     */
    private String beanName;
    /**
     * 执行的索引类型
     */
    private Integer type;
    /**
     *  索引数据
     */
    private Object data;
    /**
     * 默认增量执行新OA数据库的公文基本信息索引
     */
    private static final Integer DEFAULT_TYPE= DBIndexType.NEW_OFFICE.getType();
    /**
     * 是否立即执行
     */
    private boolean immediately;

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
