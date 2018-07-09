package com.huasisoft.search.admin.permission.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.permission.service.PSRoleGroupService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-23.
 * @Time 14:54
 * @Description PSRoleGroupService接口测试
 * @Version 2.0.0
 */
public class PSRoleGroupServiceImplTest extends BaseTest {
    @Autowired
    private PSRoleGroupService psRoleGroupService;

    @Test
    @Ignore
    public void isBelongToGroupName() throws Exception {
        psRoleGroupService.isBelongToGroupName("{AC103D7D-FFFF-FFFF-A04D-469A00000001}", "SSS");
    }

    @Test
    @Ignore
    public void isBlowMsk() throws Exception {
    }

    @Test
    @Ignore
    public void isBlowQld() throws Exception {
    }

    @Test
    @Ignore
    public void isBlowJld() throws Exception {
    }

}