package com.huasisoft.search.admin.index.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.service.IndexManagerService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 16:46
 * @Description 索引管理测试
 * @Version 2.0.0
 */
public class IndexManagerServiceTest extends BaseTest{
    @Autowired
    @Qualifier("officeIndexManagerService")
    private IndexManagerService indexManagerService;

    /**
     * 初始化索引数据
     */
   @Test
   @Ignore
    public void initIndexDataTest(){
       try {
           //IndexManagerService  indexManagerService = (IndexManagerService) ProxySpringContextsUtil.getBean("officeIndexManagerService", OfficeIndexManagerServiceImpl.class);
           IndexManagerBeans beans = new IndexManagerBeans(4);
           beans.setIncrement(false);
           beans.setIndexName("search");
           beans.setTypeName("attachment");
           boolean success = indexManagerService.initIndexData(beans);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

}
