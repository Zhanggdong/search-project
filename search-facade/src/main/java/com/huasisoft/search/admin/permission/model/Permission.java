package com.huasisoft.search.admin.permission.model;


import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-15.
 * @Time 16:48
 * @Description 部门授权
 * @Version 2.0.0
 */
public class Permission implements Serializable{
    private static final long serialVersionUID = -2134028051606014533L;

    private String permissionGUID;
    private String permissionName;
    // 授权协议代码：1 全区查询 2 管委会查询 3 两办部门查询 4 部门查询 5 接口授权
    private int code;

    public Permission() {
    }

    public Permission(String permissionGUID, String permissionName, int code) {
        this.permissionGUID = permissionGUID;
        this.permissionName = permissionName;
        this.code = code;
    }

    public String getPermissionGUID() {
        return permissionGUID;
    }

    public void setPermissionGUID(String permissionGUID) {
        this.permissionGUID = permissionGUID;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;

        if (code != that.code) return false;
        if (permissionGUID != null ? !permissionGUID.equals(that.permissionGUID) : that.permissionGUID != null)
            return false;
        return permissionName != null ? permissionName.equals(that.permissionName) : that.permissionName == null;
    }

    @Override
    public int hashCode() {
        int result = permissionGUID != null ? permissionGUID.hashCode() : 0;
        result = 31 * result + (permissionName != null ? permissionName.hashCode() : 0);
        result = 31 * result + code;
        return result;
    }
}
