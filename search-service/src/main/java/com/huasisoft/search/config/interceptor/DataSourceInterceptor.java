package com.huasisoft.search.config.interceptor;

import com.huasisoft.search.config.beans.DatabaseContextHolder;
import org.aspectj.lang.JoinPoint;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-02.
 * @Time 16:52
 * @Description 数据源切面类，通过aop来控制数据源的切换
 * @Version 2.0.0
 */
public class DataSourceInterceptor {
    public void setdataSourceMysql(JoinPoint jp) {
        DatabaseContextHolder.setCustomerType("dataSourceMySql");
    }

    public void setdataSourceOracle(JoinPoint jp) {
        DatabaseContextHolder.setCustomerType("dataSourceOracle");
    }
}
