package com.huasisoft.search.demo.mongo;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.common.util.GuidUtil;
import com.huasisoft.search.demo.model.UserMongo;
import com.huasisoft.search.demo.service.UserMongoService;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-15.
 * @Time 17:58
 * @Description MongoDB demo测试
 * @Version 2.0.0
 */
public class TestUserMongoServiceTest extends BaseTest {
    public static final Logger logger = LoggerFactory.getLogger(TestUserMongoServiceTest.class);
    @Autowired
    private UserMongoService userMongoService;

    @Test
    @Ignore
    public void insertTest() throws Exception {
        UserMongo user = new UserMongo(GuidUtil.genGuid32(),"user_test","this is use test!");
        boolean success = userMongoService.insert(user);
        if (success){
            logger.info("数据{}写入成功!",user);
        }
    }

    @Test
    @Ignore
    public void findByIdTest() throws Exception {
        UserMongo user = userMongoService.findById("2761dd85e5cf427da400e7f746fc469b");
        logger.info("查询结果为:{}",user.toString());
    }
}
