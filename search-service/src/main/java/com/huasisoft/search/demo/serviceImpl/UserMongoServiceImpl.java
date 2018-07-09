package com.huasisoft.search.demo.serviceImpl;

import com.huasisoft.search.demo.model.UserMongo;
import com.huasisoft.search.demo.repository.UserMongoRepository;
import com.huasisoft.search.demo.service.UserMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-15.
 * @Time 17:49
 * @Description UserMongoService 接口实现
 * @Version 2.0.0
 */
//@com.alibaba.dubbo.config.annotation.Service
//@Service("userMongoService")
public class UserMongoServiceImpl implements UserMongoService {

    //private final UserMongoRepository userMongoRepository;
    //private final MongoTemplate mongoTemplate;

   /* @Autowired
    public UserMongoServiceImpl(UserMongoRepository userMongoRepository, MongoTemplate mongoTemplate) {
        this.userMongoRepository = userMongoRepository;
        this.mongoTemplate = mongoTemplate;
    }*/


    @Override
    public boolean insert(UserMongo user) throws Exception {
        //mongoTemplate.insert(user);
        //userMongoRepository.save(user);
        return true;
    }

    @Override
    public UserMongo findById(String id) throws Exception {
        //return userMongoRepository.findOne(id);
        return null;
    }

    @Override
    public List<UserMongo> findAll() throws Exception {
        /*Iterable<UserMongo> iterable = userMongoRepository.findAll();
        Iterator<UserMongo> iterator = iterable.iterator();
        List<UserMongo> users = new ArrayList<>();
        while (iterator.hasNext()){
            UserMongo user = iterator.next();
            users.add(user);
        }
        return users;*/
        return null;
    }
}
