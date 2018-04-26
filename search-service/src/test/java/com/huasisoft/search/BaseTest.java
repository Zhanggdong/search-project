package com.huasisoft.search;

import com.huasisoft.search.config.AppConfig;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-26.
 * @Time 10:13
 * @Description TODO
 * @Version 2.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class BaseTest {
    @Autowired
    public TransportClient transportClient;
}
