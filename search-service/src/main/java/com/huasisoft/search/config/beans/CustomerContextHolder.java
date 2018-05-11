package com.huasisoft.search.config.beans;

import com.huasisoft.search.config.enums.DataSourceConst;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-02.
 * @Time 16:45
 * @Description TODO
 * @Version 2.0.0
 */
public class CustomerContextHolder {
    public static final String DATA_SOURCE_OA = DataSourceConst.DATA_SOURCE_OA.getDataSource();
    public static final String DATA_SOURCE_OLD_OA = DataSourceConst.DATA_SOURCE_OLD_OA.getDataSource();
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }
    public static String getCustomerType() {
        return contextHolder.get();
    }
    public static void clearCustomerType() {
        contextHolder.remove();
    }
}
