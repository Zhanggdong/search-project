package com.huasisoft.search.core.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.core.model.db.DocumentBase;
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
    public void selectByPageTest(){
        try {
            List<DocumentBase> documentBases = documentBaseService.selectByPage(1,20);
            logger.info("查询成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("查询失败！");
        }
    }

}
