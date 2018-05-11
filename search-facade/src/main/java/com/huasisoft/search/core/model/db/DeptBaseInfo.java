package com.huasisoft.search.core.model.db;

import java.io.Serializable;

public class DeptBaseInfo implements Serializable{
    private static final long serialVersionUID = 772031765250531382L;
    private String workflowinstanceGUID;

    private String senduserGUID;

    private String sendusername;

    private String departmentname;

    private String departmentGUID;

    private String dn;

    public String getWorkflowinstanceGUID() {
        return workflowinstanceGUID;
    }

    public void setWorkflowinstanceGUID(String workflowinstanceGUID) {
        this.workflowinstanceGUID = workflowinstanceGUID == null ? null : workflowinstanceGUID.trim();
    }

    public String getSenduserGUID() {
        return senduserGUID;
    }

    public void setSenduserGUID(String senduserGUID) {
        this.senduserGUID = senduserGUID == null ? null : senduserGUID.trim();
    }

    public String getSendusername() {
        return sendusername;
    }

    public void setSendusername(String sendusername) {
        this.sendusername = sendusername == null ? null : sendusername.trim();
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname == null ? null : departmentname.trim();
    }

    public String getDepartmentGUID() {
        return departmentGUID;
    }

    public void setDepartmentGUID(String departmentGUID) {
        this.departmentGUID = departmentGUID == null ? null : departmentGUID.trim();
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn == null ? null : dn.trim();
    }
}