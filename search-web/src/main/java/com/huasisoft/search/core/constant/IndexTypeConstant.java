package com.huasisoft.search.core.constant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-21.
 * @Time 11:59
 * @Description ES数据库索引常量类
 * @Version 2.0.0
 */
public @interface IndexTypeConstant {
    public static final String All = "office,document,attachment,comment";
    public static final String OFFICE = "office";
    public static final String DOCUMENT = "document";
    public static final String ATTACHMENT = "attachment";
    public static final String COMMENT = "comment";
}
