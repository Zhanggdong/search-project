package com.huasisoft.search.core.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.core.model.db.OfficeBase;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:32
 * @Description 新OA数据库公文基本信息索引接口Service测试
 * @Version 2.0.0
 */
public class OfficeBaseServiceTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(OfficeBaseServiceTest.class);

    @Autowired
    private OfficeBaseService officeBaseService;

    @Test
    public void countTest(){
        try {
            int count = officeBaseService.count();
            logger.info("查询到的数量为:{}",count);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败!");
        }
    }

    @Test
    public void selectByPage(){
        try {
            long start = System.currentTimeMillis();
            List<OfficeBase> officeBases = officeBaseService.selectByPage(1,50);
            long end = System.currentTimeMillis();
            logger.info("查询所消耗的时间为:{}秒",(end-start)%1000);
            logger.info("查询到的公文为:{}",officeBases.toString());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败!");
        }
    }
}
