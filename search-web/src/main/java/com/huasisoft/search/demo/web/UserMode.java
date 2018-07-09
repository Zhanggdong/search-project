package com.huasisoft.search.demo.web;

import java.util.Date;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-29.
 * @Time 17:02
 * @Description TODO
 * @Version 2.0.0
 */
public class UserMode {
    private String userId;
    private String name;
    private Date date;

    public UserMode(String userId, String name, Date date) {
        this.userId = userId;
        this.name = name;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
