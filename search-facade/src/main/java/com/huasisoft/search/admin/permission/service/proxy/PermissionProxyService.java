package com.huasisoft.search.admin.permission.service.proxy;

import com.huasisoft.search.admin.permission.beans.PermissionBeans;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 15:40
 * @Description 部门授权代理接口：不同地区，业务需求不一致，
 * 授权规则需要单独定义，在检索过滤中，对于部门授权来说，都是一致的数据
 * @Version 2.0.0
 */
public interface PermissionProxyService {
    public boolean initDept(PermissionBeans beans);
}
