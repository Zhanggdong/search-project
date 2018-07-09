package com.huasisoft.search.admin.index.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.index.model.DocumentBase;
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
 * @Time 15:23
 * @Description DocumentBaseService接口测试
 * @Version 2.0.0
 */
public class DocumentBaseServiceTest extends BaseTest{
    private static final Logger logger = LoggerFactory.getLogger(DocumentBaseServiceTest.class);
    @Autowired
    private DocumentBaseService documentBaseService;

    @Test
    @Ignore
    public void countTest(){
        try {
            documentBaseService.count();
            logger.info("查询成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败！");
        }
    }

    @Test
    @Ignore
    public void selectByPageTest(){
        try {
            List<DocumentBase> documentBases = documentBaseService.selectByPage(1,20);
            logger.info("查询成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败！");
        }
    }

    @Test
    @Ignore
    public void selectByPageFromTo(){
        try {
            long start = System.currentTimeMillis();
            List<DocumentBase> documentBases = documentBaseService.selectByPageFromTo(1,500);
            long end = System.currentTimeMillis();
            logger.info("查询所消耗的时间为:{}秒",(end-start)%1000);
            logger.info("查询到的附件数量为：{}",documentBases);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败！");
        }
    }


}
