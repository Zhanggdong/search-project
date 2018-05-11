package com.huasisoft.search.core.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.core.model.db.WorkflowCommentBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:43
 * @Description 新OA数据库意见基本信息索引接口Service测试
 * @Version 2.0.0
 */
public class WorkflowCommentBaseServiceTest extends BaseTest{
    private static final Logger logger = LoggerFactory.getLogger(WorkflowCommentBaseServiceTest.class);

    @Autowired
    private WorkflowCommentBaseService commentBaseService;

    @Test
    public void countTest(){
        try {
            int count = commentBaseService.count();
            logger.info("查询到的意见数量为:{}",count);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败!");
        }
    }

    @Test
    public void selectByPageTest(){
        try {
            List<WorkflowCommentBase> commentBases = commentBaseService.selectByPage(1,50);
            logger.info("查询到的意见为:{}",commentBases);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败!");
        }
    }
}
