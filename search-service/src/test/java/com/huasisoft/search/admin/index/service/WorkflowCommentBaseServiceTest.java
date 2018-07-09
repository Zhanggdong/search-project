package com.huasisoft.search.admin.index.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.index.model.WorkflowCommentBase;
import org.junit.Ignore;
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
    @Ignore
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
    @Ignore
    public void selectByPageTest(){
        try {
            Long start = System.currentTimeMillis();
            List<WorkflowCommentBase> commentBases = commentBaseService.selectByPage(1,50);
            logger.info("查询到的意见为:{}",commentBases);
            Long end = System.currentTimeMillis();
            logger.info("查询时间:{} 秒",(end-start)/1000);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败!");
        }
    }

    @Test
    //@Ignore
    public void selectByPageFromTo(){
        try {
            Long start = System.currentTimeMillis();
            List<WorkflowCommentBase> commentBases = commentBaseService.selectByPageFromTo(1,500);
            Long end = System.currentTimeMillis();
            logger.info("查询时间:{} 秒",(end-start)/1000);
            logger.info("查询到的意见为:{}",commentBases);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败!");
        }
    }
}
