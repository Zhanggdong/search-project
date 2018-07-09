package com.huasisoft.search.admin.permission.serviceImpl.proxy;

import com.huasisoft.search.admin.permission.constant.PermissionServiceConstant;
import com.huasisoft.search.admin.permission.beans.PermissionBeans;
import com.huasisoft.search.admin.index.service.IndexManagerService;
import com.huasisoft.search.admin.permission.service.PermissionService;
import com.huasisoft.search.admin.permission.service.proxy.PermissionProxyService;
import com.huasisoft.search.config.beans.ProxySpringContextsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.stereotype.Service;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 16:51
 * @Description 授权操作代理类
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service
@Service("permissionProxyService")
public class PermissionProxyServiceImpl implements PermissionProxyService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionProxyServiceImpl.class);

    @Override
    public boolean initDept(PermissionBeans beans) {
        PermissionService permissionService = getIndexManagerService(beans);
        return permissionService.init(beans);
    }

    private String getBeanName(PermissionBeans beans) {
        String beanName = PermissionServiceConstant.getBean(beans.getType());
        beans.setBeanName(beanName);
        return beanName;
    }

    private PermissionService getIndexManagerService(PermissionBeans beans) {
        // 根据参数获取需要执行的Service实现类
        String beanName = getBeanName(beans);
        PermissionService permissionService = null;
        // 获取Service实现接口
        try{
            permissionService  = (PermissionService) ProxySpringContextsUtil.getBean(beanName,PermissionService.class);
        }catch (BeanNotOfRequiredTypeException e){
            logger.info("Bean {} 转换为{}异常，请检查配置是否正确!",beanName,IndexManagerService.class);
            e.printStackTrace();
        }
        return permissionService;
    }
}
