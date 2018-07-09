package com.huasisoft.search.admin.index.serviceImpl.proxy;

import com.huasisoft.search.admin.index.constant.IndexManagerServiceType;
import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.service.IndexManagerService;
import com.huasisoft.search.admin.index.service.proxy.IndexManagerProxyService;
import com.huasisoft.search.config.beans.ProxySpringContextsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.stereotype.Service;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 15:04
 * @Description 索引操作代理接口实现
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service(timeout = 1800000)
@Service("indexManagerProxyService")
public class IndexManagerProxyServiceImpl implements IndexManagerProxyService {
    private static final Logger logger = LoggerFactory.getLogger(IndexManagerProxyServiceImpl.class);
    @Override
    public boolean existsIndexType(IndexManagerBeans beans) {
        return false;
    }

    @Override
    public boolean createIndex(IndexManagerBeans beans) {
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        boolean isCreateIndex = indexManagerService.createIndex(beans.getIndexName(),beans.getShards(),beans.getReplicas());
        return isCreateIndex;
    }

    @Override
    public boolean deleteIndex(IndexManagerBeans beans) {
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        boolean isDeleteIndex = indexManagerService.deleteIndex(beans.getIndexName());
        return isDeleteIndex;
    }

    @Override
    public boolean deleteIndexType(IndexManagerBeans beans) {
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        boolean isDeleteIndex = indexManagerService.deleteIndexType(beans.getIndexName(),beans.getTypeName());
        return isDeleteIndex;
    }

    @Override
    public boolean setMapping(IndexManagerBeans beans) throws Exception {
        // 获取具体的索引管理service
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        // 执行方法
        boolean success = indexManagerService.initIndexData(beans);
        // 返回结果
        return success;
    }

    @Override
    public boolean initIndexData(IndexManagerBeans beans) throws Exception {
        long start = System.currentTimeMillis();
        // 获取具体的索引管理service
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        // 执行方法
        boolean success = indexManagerService.initIndexData(beans);
        long end = System.currentTimeMillis();
        logger.info("索引{}{}构建时间为:{}秒",beans.getIndexName(),beans.getTypeName(),(end-start)/1000);
        // 返回结果
        return success;
    }

    private IndexManagerService getIndexManagerService(IndexManagerBeans beans) {
        // 根据参数获取需要执行的Service实现类
        String beanName = getBeanName(beans);
        IndexManagerService indexManagerService = null;
        // 获取Service实现接口
        try{
            indexManagerService  = (IndexManagerService) ProxySpringContextsUtil.getBean(beanName,IndexManagerService.class);
        }catch (BeanNotOfRequiredTypeException e){
            logger.info("Bean {} 转换为{}异常，请检查配置是否正确!",beanName,IndexManagerService.class);
            e.printStackTrace();
        }
        return indexManagerService;
    }

    private String getBeanName(IndexManagerBeans beans) {
        String beanName = IndexManagerServiceType.getBean(beans.getType());
        beans.setBeanName(beanName);
        return beanName;
    }

    @Override
    public boolean stop(IndexManagerBeans beans) throws Exception {
        // 获取具体的索引管理service
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        return indexManagerService.stop(beans);
    }

    @Override
    public int count(IndexManagerBeans beans) throws Exception {
        // 获取具体的索引管理service
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        return indexManagerService.count();
    }

    @Override
    public boolean initIndexData(int from, int to, IndexManagerBeans beans) throws Exception {
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        return indexManagerService.initIndexData(from,to,beans);
    }
}