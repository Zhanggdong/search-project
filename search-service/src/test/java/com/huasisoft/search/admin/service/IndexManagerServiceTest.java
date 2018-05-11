package com.huasisoft.search.admin.service;

import com.huasisoft.search.BaseTest;
import com.huasisoft.search.admin.model.IndexManagerBeans;
import com.huasisoft.search.admin.serviceImpl.OfficeIndexManagerServiceImpl;
import com.huasisoft.search.config.beans.ProxySpringContextsUtil;
import com.huasisoft.search.core.model.es.ESOfficeIndex;
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
    public void initIndexDataTest(){
       try {
           //IndexManagerService  indexManagerService = (IndexManagerService) ProxySpringContextsUtil.getBean("officeIndexManagerService", OfficeIndexManagerServiceImpl.class);
           IndexManagerBeans beans = new IndexManagerBeans(1);
           beans.setIndexName("search");
           beans.setTypeName("office");
           boolean success = indexManagerService.initIndexData(beans);
       } catch (Exception e) {
           e.printStackTrace();
       }
   }

}
