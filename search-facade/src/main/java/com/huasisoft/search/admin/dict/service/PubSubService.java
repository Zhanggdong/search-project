package com.huasisoft.search.admin.dict.service;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 15:58
 * @Description TODO
 * @Version 2.0.0
 */
public interface PubSubService {
    public void publishAsync(String channel,String message);
}
