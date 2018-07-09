package com.huasisoft.search.demo.repository;

import com.huasisoft.search.demo.model.RedisStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 16:40
 * @Description TODO
 * @Version 2.0.0
 */
@Repository
public interface RedisStudentRepository extends CrudRepository<RedisStudent,String>{

}
