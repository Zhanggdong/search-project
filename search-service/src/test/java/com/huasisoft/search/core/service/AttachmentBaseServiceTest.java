package com.huasisoft.search.core.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.core.model.db.AttachmentBase;
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
    public void selectByPageTest() throws Exception{
        List<AttachmentBase> attachmentBases = attachmentBaseService.selectByPage(1,10);
        logger.info("查询到的附件数量为：{}",attachmentBases);
    }
}
