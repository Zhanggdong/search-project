package com.huasisoft.search.admin.permission.beans;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 16:45
 * @Description 授权参数对象
 * @Version 2.0.0
 */
public class PermissionBeans implements Serializable{
    private static final long serialVersionUID = -8504241686104010350L;
    /**
     * 授权操作类型：1 部门授权 2 接口授权
     */
    private Integer type;
    /**
     * 授权业务标识：1 坪山新区
     */
    private Integer code;

    /**
     * 授权的内容：可以是接口，也可以是部门，传入JSON数据
     */
    private Object content;

    /**
     * 授权对应的实现类Bean
     */
    private String beanName;

    /**
     * 用户ID
     */
    private String employeeGUID;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getEmployeeGUID() {
        return employeeGUID;
    }

    public void setEmployeeGUID(String employeeGUID) {
        this.employeeGUID = employeeGUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PermissionBeans that = (PermissionBeans) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (beanName != null ? !beanName.equals(that.beanName) : that.beanName != null) return false;
        return employeeGUID != null ? employeeGUID.equals(that.employeeGUID) : that.employeeGUID == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (beanName != null ? beanName.hashCode() : 0);
        result = 31 * result + (employeeGUID != null ? employeeGUID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PermissionBeans{" +
                "type=" + type +
                ", code=" + code +
                ", content=" + content +
                ", beanName='" + beanName + '\'' +
                ", employeeGUID='" + employeeGUID + '\'' +
                '}';
    }
}
