package com.huasisoft.search.admin.permission.repository;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.permission.model.Permission;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 18:03
 * @Description TODO
 * @Version 2.0.0
 */
public class PermissionRepositoryTest extends BaseTest {
    @Autowired
    private PermissionRepository permissionRepository;
    @Test
    @Ignore
    public void remove() throws Exception {

    }

    @Test
    @Ignore
    public void getByEmployeeGuid() throws Exception {
        List<Permission> permissions = permissionRepository.getByEmployeeGuid("{BFA80606-0000-0000-6495-B24900000003}");
        logger.info("查询到的授权信息为：{}",permissions.toString());
    }

}