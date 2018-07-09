package com.huasisoft.search.admin.permission.service;

import com.huasisoft.search.admin.permission.beans.PermissionBeans;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 17:03
 * @Description 授权操作接口
 * @Version 2.0.0
 */
public interface PermissionService<T>{
    public boolean init(PermissionBeans beans);

    public boolean add(PermissionBeans beans);

    public boolean update(PermissionBeans beans);

    public boolean remove(PermissionBeans beans);

    public List<T> getPermission(String employeeGUID,int pageNum ,int pageSize);
}
