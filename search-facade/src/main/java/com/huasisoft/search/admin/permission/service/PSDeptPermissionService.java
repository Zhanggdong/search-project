package com.huasisoft.search.admin.permission.service;

import com.huasisoft.search.admin.permission.constant.PositionGroup;
import com.huasisoft.search.admin.permission.model.Department;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 10:42
 * @Description 部门查询授权操作接口,该接口是坪山新区业务规则实现
 * @Version 2.0.0
 */
public interface PSDeptPermissionService extends PermissionService{
    /**
     * 根据人员和岗位查询授权的部门
     * @param userGUID
     * @param positionName
     * @return
     */
    public List<Department> getDepartmentGuids(String userGUID, PositionGroup positionName);

}
