package com.huasisoft.search.admin.dict.mq;

import redis.clients.jedis.JedisPubSub;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 10:16
 * @Description 词典修改操作MQ任务类
 * @Version 2.0.0
 */
public class MQTask implements Runnable{

    private String[] channel = null;//监听的消息通道
    private JedisPubSub jedisPubSub = null;//消息处理任务

    public MQTask(String[] channel, JedisPubSub jedisPubSub) {
        this.channel = channel;
        this.jedisPubSub = jedisPubSub;
    }

    @Override
    public void run() {

    }
}
