package com.huasisoft.search.admin.permission.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.permission.model.Employee;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 16:09
 * @Description TODO
 * @Version 2.0.0
 */
public class EmployeeServiceImplTest extends BaseTest {
    public static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImplTest.class);

    @Autowired
    private EmployeeService employeeService;

    @Test
    @Ignore
    public void count(){
        try {
            int count = employeeService.count();
            logger.info("查询到的记录为:{}",count);
        }catch (Exception e){
            logger.info("查询到失败!");
            e.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void selectByPage(){
        try {
            List<Employee> employees = employeeService.selectByPage(1,500);
            logger.info("查询到的记录为:{}",employees);
        }catch (Exception e){
            logger.info("查询到失败!");
            e.printStackTrace();
        }
    }

}