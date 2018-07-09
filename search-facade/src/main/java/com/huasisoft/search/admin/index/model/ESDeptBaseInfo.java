package com.huasisoft.search.admin.index.model;

import java.io.Serializable;

public class ESDeptBaseInfo implements Serializable{
    private static final long serialVersionUID = -8195926528464906229L;
    private String senduserGUID;

    private String sendusername;

    private String departmentname;

    private String departmentGUID;

    private String dn;

    public String getSenduserGUID() {
        return senduserGUID;
    }

    public void setSenduserGUID(String senduserGUID) {
        this.senduserGUID = senduserGUID;
    }

    public String getSendusername() {
        return sendusername;
    }

    public void setSendusername(String sendusername) {
        this.sendusername = sendusername;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getDepartmentGUID() {
        return departmentGUID;
    }

    public void setDepartmentGUID(String departmentGUID) {
        this.departmentGUID = departmentGUID;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ESDeptBaseInfo that = (ESDeptBaseInfo) o;

        if (senduserGUID != null ? !senduserGUID.equals(that.senduserGUID) : that.senduserGUID != null) return false;
        if (sendusername != null ? !sendusername.equals(that.sendusername) : that.sendusername != null) return false;
        if (departmentname != null ? !departmentname.equals(that.departmentname) : that.departmentname != null)
            return false;
        if (departmentGUID != null ? !departmentGUID.equals(that.departmentGUID) : that.departmentGUID != null)
            return false;
        return dn != null ? dn.equals(that.dn) : that.dn == null;
    }

    @Override
    public int hashCode() {
        int result = senduserGUID != null ? senduserGUID.hashCode() : 0;
        result = 31 * result + (sendusername != null ? sendusername.hashCode() : 0);
        result = 31 * result + (departmentname != null ? departmentname.hashCode() : 0);
        result = 31 * result + (departmentGUID != null ? departmentGUID.hashCode() : 0);
        result = 31 * result + (dn != null ? dn.hashCode() : 0);
        return result;
    }
}