package com.huasisoft.search.admin.permission.service;

import com.huasisoft.search.admin.permission.model.Employee;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 15:45
 * @Description 坪山人员操作接口
 * @Version 2.0.0
 */
public interface EmployeeService {
    public int count();
    public List<Employee> selectByPage(int pageNum,int pageSize);
}
