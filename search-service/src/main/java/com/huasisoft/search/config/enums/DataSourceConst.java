package com.huasisoft.search.config.enums;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-02.
 * @Time 16:47
 * @Description 数据源常量
 * @Version 2.0.0
 */
public enum DataSourceConst {
    DATA_SOURCE_OA("dataSource"),
    DATA_SOURCE_OLD_OA("dataSource2");

    private String dataSource;

    DataSourceConst(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSource() {
        return dataSource;
    }
}
