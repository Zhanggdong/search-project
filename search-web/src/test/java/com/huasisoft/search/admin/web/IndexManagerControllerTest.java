package com.huasisoft.search.admin.web;

import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 18:18
 * @Description TODO
 * @Version 2.0.0
 */
public class IndexManagerControllerTest{
    @Test
    public void createIndex() throws Exception {

    }

    @Test
    public void initIndexData() throws Exception {
        IndexManagerBeans beans = new IndexManagerBeans();
        beans.setType(3);
        beans.setTypeName("search");
        beans.setIndexName("comment");
        beans.setImmediately(false);

    }

}