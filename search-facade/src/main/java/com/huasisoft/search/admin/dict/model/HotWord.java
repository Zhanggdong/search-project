package com.huasisoft.search.admin.dict.model;


import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 10:44
 * @Description 热词
 * @Version 2.0.0
 */
public class HotWord implements Serializable{
    private static final long serialVersionUID = -5803792614836971748L;
    private String id;
    private String name;
    private byte deleted;// 是否删除 （1 表示删除 0 表示未删除）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getDeleted() {
        return deleted;
    }

    public void setDeleted(byte deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HotWord hotWord = (HotWord) o;

        if (deleted != hotWord.deleted) return false;
        if (id != null ? !id.equals(hotWord.id) : hotWord.id != null) return false;
        return name != null ? name.equals(hotWord.name) : hotWord.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) deleted;
        return result;
    }

    @Override
    public String toString() {
        return "SynonymWord{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
