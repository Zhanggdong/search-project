package com.huasisoft.search.admin.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 14:38
 * @Description 索引实现常量
 * @Version 2.0.0
 */
public class IndexManagerServiceType {
    private static Map<Integer, String> beanMap = new ConcurrentHashMap<Integer, String>();
    static {
        beanMap.put(1, "officeIndexManagerService");
        beanMap.put(5, "officeIndexManagerService");
        beanMap.put(2, "documentIndexManagerService");
        beanMap.put(6, "documentIndexManagerService");
        beanMap.put(3, "commentIndexManagerService");
        beanMap.put(7, "commentIndexManagerService");
        beanMap.put(4, "attachmentIndexManagerService");
        beanMap.put(8, "attachmentIndexManagerService");
    }

    private IndexManagerServiceType() {}

    public static String getBean(Integer type){
        String beanName = null;
        if(type == null){
            // 默认获取公文索引实现类的BeanName
            beanName = IndexManagerServiceType.beanMap.get(1);
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
