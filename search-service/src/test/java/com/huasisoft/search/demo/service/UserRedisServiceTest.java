package com.huasisoft.search.demo.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.demo.model.RedisStudent;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 17:18
 * @Description 测试
 * @Version 2.0.0
 */
public class UserRedisServiceTest extends BaseTest {

    @Autowired
    private UserRedisService userRedisService;

    @Test
    @Ignore
    public void save() throws Exception {
        RedisStudent student = new RedisStudent("Eng2015001", "John Doe", RedisStudent.Gender.MALE, 1);
        userRedisService.save(student);
    }

    @Test
    @Ignore
    public void saveBatch() throws Exception {
        RedisStudent engStudent = new RedisStudent(
                "Eng2015001", "John Doe", RedisStudent.Gender.MALE, 1);
        RedisStudent medStudent = new RedisStudent(
                "Med2015001", "Gareth Houston", RedisStudent.Gender.MALE, 2);
        List<RedisStudent> students = new ArrayList<>(2);
        students.add(engStudent);
        students.add(medStudent);
        userRedisService.saveBatch(students);
    }

    @Test
    public void findById() throws Exception {
        RedisStudent student = userRedisService.findById("Eng2015001");
    }

    @Test
    @Ignore
    public void findAll() throws Exception {
        List<RedisStudent> students = userRedisService.findAll();
    }

    @Test
    @Ignore
    public void update() throws Exception {
        RedisStudent student = new RedisStudent("Eng2015001", "John Doe", RedisStudent.Gender.MALE, 1);
        student.setName("Richard Watson");
        userRedisService.save(student);
    }

    @Test
    @Ignore
    public void deleteById() throws Exception {
        userRedisService.deleteById("Eng2015001");
    }

}