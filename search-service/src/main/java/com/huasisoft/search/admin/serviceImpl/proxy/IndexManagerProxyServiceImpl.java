package com.huasisoft.search.admin.serviceImpl.proxy;

import com.alibaba.dubbo.config.annotation.Service;
import com.huasisoft.search.admin.constant.IndexManagerServiceType;
import com.huasisoft.search.admin.model.IndexManagerBeans;
import com.huasisoft.search.admin.service.IndexManagerService;
import com.huasisoft.search.admin.service.proxy.IndexManagerProxyService;
import com.huasisoft.search.config.beans.ProxySpringContextsUtil;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 15:04
 * @Description 索引操作代理接口实现
 * @Version 2.0.0
 */
@Service
public class IndexManagerProxyServiceImpl implements IndexManagerProxyService {
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
        return false;
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
        // 获取具体的索引管理service
        IndexManagerService indexManagerService = getIndexManagerService(beans);
        // 执行方法
        boolean success = indexManagerService.initIndexData(beans);
        // 返回结果
        return success;
    }

    private IndexManagerService getIndexManagerService(IndexManagerBeans beans) {
        // 根据参数获取需要执行的Service实现类
        String beanName = getBeanName(beans);
        // 获取Service实现接口
        return (IndexManagerService) ProxySpringContextsUtil.getBean(beanName,IndexManagerService.class);
    }

    private String getBeanName(IndexManagerBeans beans) {
        String beanName = IndexManagerServiceType.getBean(beans.getType());
        beans.setBeanName(beanName);
        return beanName;
    }
}
