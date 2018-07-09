package com.huasisoft.search.admin.permission.repository;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.admin.permission.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 17:27
 * @Description TODO
 * @Version 2.0.0
 */
@Repository
public class PermissionRepository {
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean remove(List<Permission> permissions,String employeeGUID){
        for (Permission permission:permissions){
            String json = JSONObject.toJSONString(permission);
            redisTemplate.opsForList().remove("search:"+employeeGUID,0,json);
        }
        return false;
    }

    public List<Permission> getByEmployeeGuid(String employeeGUID){
        List<Permission> permissions = new ArrayList<>();
        Set<Permission> set = redisTemplate.keys("search:"+employeeGUID);
        Iterator<Permission> iterator = set.iterator();
        while (iterator.hasNext()){
            permissions.add(iterator.next());
        }
        return permissions;
    }

    public List<Permission> getPermission(String employeeGUID, int pageNum, int pageSize) {
        long start = 0;
        long end = 0;
        if (pageNum==0){
            start = pageNum;
            end = pageSize-1;
        }else {
            start = (pageNum-1)*pageSize+1;
            end = pageNum*pageSize;
        }
        List<Permission> permissions = redisTemplate.opsForList().range("search:"+employeeGUID,start-1,end-1);
        return permissions;
    }

}
