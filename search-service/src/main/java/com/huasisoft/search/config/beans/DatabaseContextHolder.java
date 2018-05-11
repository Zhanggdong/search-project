package com.huasisoft.search.config.beans;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-02.
 * @Time 16:54
 * @Description 数据源类型Context
 * @Version 2.0.0
 */
public class DatabaseContextHolder {

    private static class LazyHolder {
        //final 为了防止内部误操作，代理模式，GgLib的代理模式
        private static final DatabaseContextHolder INSTANCE = new DatabaseContextHolder();
    }

    private String customerType;

    private DatabaseContextHolder() {
    }

    public static final DatabaseContextHolder getInstance() {

        //方法中的逻辑，是要在用户调用的时候才开始执行的
        //方法中实现逻辑需要分配内存，也是调用时才分配的
        return LazyHolder.INSTANCE;
    }

    public String getCustomerType() {
        return getInstance().customerType;
    }

    public static void setCustomerType(String customerType) {
        getInstance().customerType = customerType;
    }
}
