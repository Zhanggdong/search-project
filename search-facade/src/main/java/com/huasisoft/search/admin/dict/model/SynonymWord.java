package com.huasisoft.search.admin.dict.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 15:38
 * @Description 同义词
 * @Version 2.0.0
 */
public class SynonymWord implements Serializable{
    private String id;
    private static final long serialVersionUID = 3835762443926096288L;
    private String word;
    private int deleted;// 是否删除 （1 表示删除 0 表示未删除）
    private Timestamp createTime;
    private Timestamp updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SynonymWord that = (SynonymWord) o;

        if (deleted != that.deleted) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (word != null ? !word.equals(that.word) : that.word != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return updateTime != null ? updateTime.equals(that.updateTime) : that.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (word != null ? word.hashCode() : 0);
        result = 31 * result + deleted;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SynonymWord{" +
                "id='" + id + '\'' +
                ", word='" + word + '\'' +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
