package com.huasisoft.search.admin.permission.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 16:55
 * @Description 授权常量获取
 * @Version 2.0.0
 */
public class PermissionServiceConstant {
    private static Map<Integer, String> beanMap = new ConcurrentHashMap<Integer, String>();
    static {
        beanMap.put(1, "psDeptPermissionService");
    }

    private PermissionServiceConstant() {}

    public static String getBean(Integer type){
        String beanName = null;
        if(type == null){
            // 默认获取坪山实现类的BeanName
            beanName = PermissionServiceConstant.beanMap.get(1);
        }
        if(beanMap.containsKey(type)){
            try {
                beanName = beanMap.get(type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return beanName;
    }
}
