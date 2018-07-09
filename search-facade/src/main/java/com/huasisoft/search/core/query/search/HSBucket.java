package com.huasisoft.search.core.query.search;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 15:07
 * @Description 自定义Elasticsearch返回的分组Bucket对象，调用方不需要引入ES相关jar包
 * @Version 2.0.0
 */
public class HSBucket implements Serializable{
    private String key;//分组后key 值（如根据分类id 分组的话，key 为分类id 的值）
    private String doc_count;//key 相对应的分组总数

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDoc_count() {
        return doc_count;
    }

    public void setDoc_count(String doc_count) {
        this.doc_count = doc_count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HSBucket bucket = (HSBucket) o;

        if (key != null ? !key.equals(bucket.key) : bucket.key != null) return false;
        return doc_count != null ? doc_count.equals(bucket.doc_count) : bucket.doc_count == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (doc_count != null ? doc_count.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HSBucket{" +
                "key='" + key + '\'' +
                ", doc_count='" + doc_count + '\'' +
                '}';
    }
}
