package com.huasisoft.search.demo.service;

import com.huasisoft.search.demo.model.RedisStudent;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 16:26
 * @Description Redis 集成测试
 * @Version 2.0.0
 */
public interface UserRedisService {
    public boolean save(RedisStudent student);
    public boolean saveBatch(List<RedisStudent> student);
    public RedisStudent findById(String id);
    public List<RedisStudent> findAll();
    public boolean update(RedisStudent students);
    public boolean deleteById(String id);
}
