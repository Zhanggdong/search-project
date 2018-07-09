package com.huasisoft.search.demo.service;

import com.huasisoft.search.demo.model.UserMongo;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-15.
 * @Time 17:46
 * @Description Service接口，为测试demo
 * @Version 2.0.0
 */
public interface UserMongoService {
    public boolean insert(UserMongo user) throws Exception;
    public UserMongo findById(String id) throws Exception;
    public List<UserMongo> findAll() throws Exception;
}
