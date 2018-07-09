package com.huasisoft.search.admin.index.service;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.index.model.AttachmentBase;
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
 * @Time 11:47
 * @Description 附件操作接口Service测试
 * @Version 2.0.0
 */
public class AttachmentBaseServiceTest extends BaseTest {
    public static final Logger logger = LoggerFactory.getLogger(AttachmentBaseServiceTest.class);

    @Autowired
    private AttachmentBaseService attachmentBaseService;

    @Test
    @Ignore
    public void countTest() throws Exception{
        logger.info("需要构建附件索引的数量为：{}",attachmentBaseService.count());
    }

    @Test
    @Ignore
    public void selectByPageTest() throws Exception{
        long start = System.currentTimeMillis();
        List<AttachmentBase> attachmentBases = attachmentBaseService.selectByPage(1,500);
        long end = System.currentTimeMillis();
        logger.info("查询到的附件数量为：{}",attachmentBases);
        logger.info("查询所消耗的时间为:{}秒",(end-start)%1000);
    }

    @Test
    @Ignore
    public void selectByPageFromTo() throws Exception{
        try {
            long start = System.currentTimeMillis();
            List<AttachmentBase> attachmentBases = attachmentBaseService.selectByPageFromTo(2000,3000);
            long end = System.currentTimeMillis();
            logger.info("查询所消耗的时间为:{}秒",(end-start)%1000);
            logger.info("查询到的附件数量为：{}",attachmentBases);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
