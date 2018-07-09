package com.huasisoft.search.demo.serviceImpl;

import com.huasisoft.search.demo.model.RedisStudent;
import com.huasisoft.search.demo.repository.RedisStudentRepository;
import com.huasisoft.search.demo.service.UserRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 16:47
 * @Description Redis demo接口实现
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service
@Service("userRedisService")
public class UserRedisServiceImpl implements UserRedisService{

    private static final Logger logger = LoggerFactory.getLogger(UserRedisServiceImpl.class);

    @Autowired
    private RedisStudentRepository studentRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean save(RedisStudent student) {
        studentRepository.save(student);
        return true;
    }

    @Override
    public boolean saveBatch(List<RedisStudent> students) {
        studentRepository.save(students);
        return true;
    }

    @Override
    public RedisStudent findById(String id) {
        return  studentRepository.findOne(id);
    }

    @Override
    public List<RedisStudent> findAll() {
        List<RedisStudent> students = new ArrayList<>();
        Iterable<RedisStudent> iterable = studentRepository.findAll();
        Iterator<RedisStudent> iterator = iterable.iterator();
        while (iterator.hasNext()){
            students.add(iterator.next());
        }
        return students;
    }

    @Override
    public boolean update(RedisStudent student) {
        studentRepository.save(student);
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        studentRepository.delete(id);
        return true;
    }
}
