package com.huasisoft.search.admin.dict.model;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 15:42
 * @Description MQ消息定义
 * @Version 2.0.0
 */
public class RedisQueueMessage<T> implements Serializable{
    private static final long serialVersionUID = 2433558093027228417L;

    int bodyCode;// 协议类型，根据业务自己定义
    int opt;// 操作协议编码，1 新增 2 删除 3 更新
    T body;// 消息体

    public RedisQueueMessage() {

    }

    public RedisQueueMessage(int bodyCode, int opt, T body) {
        this.bodyCode = bodyCode;
        this.opt = opt;
        this.body = body;
    }

    public int getBodyCode() {
        return bodyCode;
    }

    public void setBodyCode(int bodyCode) {
        this.bodyCode = bodyCode;
    }

    public int getOpt() {
        return opt;
    }

    public void setOpt(int opt) {
        this.opt = opt;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RedisQueueMessage<?> that = (RedisQueueMessage<?>) o;

        if (bodyCode != that.bodyCode) return false;
        if (opt != that.opt) return false;
        return body != null ? body.equals(that.body) : that.body == null;
    }

    @Override
    public int hashCode() {
        int result = bodyCode;
        result = 31 * result + opt;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }
}
