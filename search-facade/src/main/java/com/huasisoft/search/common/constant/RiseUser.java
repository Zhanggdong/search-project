package com.huasisoft.search.common.constant;

import com.huasisoft.search.admin.permission.model.Department;
import com.huasisoft.search.admin.permission.model.Employee;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-23.
 * @Time 10:08
 * @Description 登录的用户对象
 * @Version 2.0.0
 */
public class RiseUser implements Serializable{
    private static final long serialVersionUID = -7515071637741452187L;
    private String rcUID;
    private String loginName;
    private String userGUID;
    private String userName;
    private String userDN;
    private String departmentGUID;
    private String departmentName;
    private String bureauGUID;
    private String bureauName;
    private String phone;
    private Employee employee = null;
    private Department department = null;

    public RiseUser(String userGUID) {
        this.rcUID = "";
        this.loginName = "";
        this.userGUID = "";
        this.userName = "";
        this.userDN = "";
        this.departmentGUID = "";
        this.departmentName = "";
        this.phone = "";
    }

    public String getRcUID() {
        return rcUID;
    }

    public void setRcUID(String rcUID) {
        this.rcUID = rcUID;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserGUID() {
        return userGUID;
    }

    public void setUserGUID(String userGUID) {
        this.userGUID = userGUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDN() {
        return userDN;
    }

    public void setUserDN(String userDN) {
        this.userDN = userDN;
    }

    public String getDepartmentGUID() {
        return departmentGUID;
    }

    public void setDepartmentGUID(String departmentGUID) {
        this.departmentGUID = departmentGUID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getBureauGUID() {
        return bureauGUID;
    }

    public void setBureauGUID(String bureauGUID) {
        this.bureauGUID = bureauGUID;
    }

    public String getBureauName() {
        return bureauName;
    }

    public void setBureauName(String bureauName) {
        this.bureauName = bureauName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RiseUser riseUser = (RiseUser) o;

        if (rcUID != null ? !rcUID.equals(riseUser.rcUID) : riseUser.rcUID != null) return false;
        if (loginName != null ? !loginName.equals(riseUser.loginName) : riseUser.loginName != null) return false;
        if (userGUID != null ? !userGUID.equals(riseUser.userGUID) : riseUser.userGUID != null) return false;
        if (userName != null ? !userName.equals(riseUser.userName) : riseUser.userName != null) return false;
        if (userDN != null ? !userDN.equals(riseUser.userDN) : riseUser.userDN != null) return false;
        if (departmentGUID != null ? !departmentGUID.equals(riseUser.departmentGUID) : riseUser.departmentGUID != null)
            return false;
        if (departmentName != null ? !departmentName.equals(riseUser.departmentName) : riseUser.departmentName != null)
            return false;
        if (bureauGUID != null ? !bureauGUID.equals(riseUser.bureauGUID) : riseUser.bureauGUID != null) return false;
        if (bureauName != null ? !bureauName.equals(riseUser.bureauName) : riseUser.bureauName != null) return false;
        if (phone != null ? !phone.equals(riseUser.phone) : riseUser.phone != null) return false;
        if (employee != null ? !employee.equals(riseUser.employee) : riseUser.employee != null) return false;
        return department != null ? department.equals(riseUser.department) : riseUser.department == null;
    }

    @Override
    public int hashCode() {
        int result = rcUID != null ? rcUID.hashCode() : 0;
        result = 31 * result + (loginName != null ? loginName.hashCode() : 0);
        result = 31 * result + (userGUID != null ? userGUID.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userDN != null ? userDN.hashCode() : 0);
        result = 31 * result + (departmentGUID != null ? departmentGUID.hashCode() : 0);
        result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
        result = 31 * result + (bureauGUID != null ? bureauGUID.hashCode() : 0);
        result = 31 * result + (bureauName != null ? bureauName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (department != null ? department.hashCode() : 0);
        return result;
    }
}
