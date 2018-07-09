package com.huasisoft.search.admin.index.service;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.index.model.DeptBaseInfo;
import com.huasisoft.search.admin.index.service.DeptBaseInfoService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-15.
 * @Time 8:44
 * @Description TODO
 * @Version 2.0.0
 */
public class DeptBaseServiceTest extends BaseTest{
    @Autowired
    private DeptBaseInfoService deptBaseInfoService;

    @Test
    @Ignore
    public void selectByWorkflowGuid(){
        long start = System.currentTimeMillis();
        List<DeptBaseInfo> deptBaseInfoList = deptBaseInfoService.selectByWorkflowinstanceGUID("{BFA80602-FFFF-FFFF-9BBC-B7C7000010A0}");
        long end = System.currentTimeMillis();
        logger.info("查询所消耗的时间为:{}秒",(end-start)%1000);
        String json = JSONObject.toJSONString(deptBaseInfoList);
        logger.info("查询结果为：{}",json);
    }
}
