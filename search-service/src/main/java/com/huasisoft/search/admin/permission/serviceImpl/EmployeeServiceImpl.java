package com.huasisoft.search.admin.permission.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.huasisoft.search.admin.permission.mapper.EmployeeMapper;
import com.huasisoft.search.admin.permission.model.Employee;
import com.huasisoft.search.admin.permission.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 16:01
 * @Description 坪山人员操作接口
 * @Version 2.0.0
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public int count() {
        return employeeMapper.count();
    }

    @Override
    public List<Employee> selectByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return employeeMapper.selectByPage();
    }
}
