package com.huasisoft.search.admin.dict.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 10:38
 * @Description 停词
 * @Version 2.0.0
 */
public class StopWord implements Serializable{
    private static final long serialVersionUID = 6562057108142084469L;
    private String word;
    private int deleted;// 是否删除 （1 表示删除 0 表示未删除）
    private Timestamp createTime;
    private Timestamp updateTime;

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

        StopWord stopWord = (StopWord) o;

        if (deleted != stopWord.deleted) return false;
        if (word != null ? !word.equals(stopWord.word) : stopWord.word != null) return false;
        if (createTime != null ? !createTime.equals(stopWord.createTime) : stopWord.createTime != null) return false;
        return updateTime != null ? updateTime.equals(stopWord.updateTime) : stopWord.updateTime == null;
    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + deleted;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
