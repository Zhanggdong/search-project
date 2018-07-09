package com.huasisoft.search.admin.permission.serviceImpl;

import com.huasisoft.search.admin.permission.beans.PermissionBeans;
import com.huasisoft.search.admin.permission.repository.PermissionRepository;
import com.huasisoft.search.admin.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-31.
 * @Time 10:38
 * @Description 授权操作接口抽象实现类
 * @Version 2.0.0
 */
public class AbstractPermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public boolean init(PermissionBeans beans) {
        return false;
    }

    @Override
    public boolean add(PermissionBeans beans) {
        return false;
    }

    @Override
    public boolean update(PermissionBeans beans) {
        return false;
    }

    @Override
    public boolean remove(PermissionBeans beans) {
        return false;
    }

    @Override
    public List getPermission(String employeeGUID, int pageNum, int pageSize) {
        return permissionRepository.getPermission(employeeGUID,pageNum,pageSize);
    }
}
