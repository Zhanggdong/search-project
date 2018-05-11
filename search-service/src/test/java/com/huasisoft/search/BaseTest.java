package com.huasisoft.search;

import com.huasisoft.search.config.AppConfig;
import com.huasisoft.search.demo.util.ESUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.After;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-26.
 * @Time 10:13
 * @Description 测试Base
 * @Version 2.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class BaseTest {

    public static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Autowired
    public TransportClient transportClient;

    @After
    public void tearDown() throws Exception {
        if (transportClient != null) {
            transportClient.close();
        }
        logger.info("{}","关闭成功!");
    }

    protected void println(SearchResponse searchResponse) {
        ESUtils.println(searchResponse);
    }
}
