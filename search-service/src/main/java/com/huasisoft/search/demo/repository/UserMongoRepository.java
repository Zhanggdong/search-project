package com.huasisoft.search.demo.repository;

import com.huasisoft.search.demo.model.UserMongo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-15.
 * @Time 17:50
 * @Description UserMongo Repository
 * @Version 2.0.0
 */
public interface UserMongoRepository extends CrudRepository<UserMongo,String>{
}
