package com.huasisoft.search.admin.permission.service;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 10:44
 * @Description 坪山查询角色组操作接口
 * @Version 2.0.0
 */
public interface PSRoleGroupService {
    /**
     * 判断当前用户是否属于指定全局用户组
     * @param userGUID 用户ID
     * @param userGroupName 组名称
     * @return
     */
    boolean isBelongToGroupName(String userGUID, String userGroupName);
    /**
     * 是否为秘书科
     * @param userGUID 用户ID
     * @return
     */
    boolean isBlowMsk(String userGUID);

    /**
     * 是否区领导
     * @param userGUID 用户ID
     * @return
     */
    boolean isBlowQld(String userGUID);

    /**
     * 判断是否为局领导
     * @param userGUID 用户ID
     * @param breauGUID 用户所在局办ID
     * @return
     */
    boolean isBlowJld(String userGUID,String breauGUID);
}
