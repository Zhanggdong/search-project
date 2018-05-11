package com.huasisoft.search.config.beans;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-02.
 * @Time 16:43
 * @Description 动态数据源
 * @Version 2.0.0
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return CustomerContextHolder.getCustomerType();
    }
}
