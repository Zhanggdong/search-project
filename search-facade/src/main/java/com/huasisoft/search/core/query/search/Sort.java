package com.huasisoft.search.core.query.search;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 11:20
 * @Description 排序
 * @Version 2.0.0
 */
public class Sort implements Serializable{
    private static final long serialVersionUID = 6317401709604818613L;
    private String key;//排序的key
    private Byte type;//1、asc, 2、desc

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sort sort = (Sort) o;

        if (key != null ? !key.equals(sort.key) : sort.key != null) return false;
        return type != null ? type.equals(sort.type) : sort.type == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
