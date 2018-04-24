package com.huasisoft.search.demo.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;


import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-23.
 * @Time 11:19
 * @Description 文档类对象
 * @Version 2.0.0
 */
@Document(indexName = "test_log",type = "system_log")
public class Logs implements Serializable{
    private static final long serialVersionUID = -7584671049185123560L;

    @Field(type= FieldType.Integer,index= false,store=true)
    private Integer userId;
    @Field(type=FieldType.Integer,index= false,store=true)
    private String system;
    @Field(type=FieldType.keyword,index=false,store=true)
    private String url;
    @Field(type=FieldType.text,index=true,store=true)
    private String content;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Logs logs = (Logs) o;

        if (!userId.equals(logs.userId)) return false;
        if (!system.equals(logs.system)) return false;
        if (!url.equals(logs.url)) return false;
        return content.equals(logs.content);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + system.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Logs{" +
                "userId=" + userId +
                ", system='" + system + '\'' +
                ", url='" + url + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
