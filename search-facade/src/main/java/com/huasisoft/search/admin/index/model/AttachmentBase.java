package com.huasisoft.search.admin.index.model;

import com.huasisoft.search.admin.index.constant.AbstractIndexRequest;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:32
 * @Description 附件基本信息视图对象
 * @Version 2.0.0
 */
public class AttachmentBase extends AbstractIndexRequest implements Serializable{

    private static final long serialVersionUID = 3584072700166564962L;
    private String id;

    private String guid;

    private String title;

    private String fjname;

    private Integer bwtype;

    private String banwenbianhao;

    private String laiwendanwei;

    private String createdate;

    private Timestamp createdatetime;

    private Timestamp lastmodified;

    private String url;

    private String creatorGUID;

    private String creatorname;

    private String dn;

    private Integer type;

    private String departmentguid;

    private String bureauname;

    // 办件所经手的部门
    private List<DeptBaseInfo> departments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFjname() {
        return fjname;
    }

    public void setFjname(String fjname) {
        this.fjname = fjname;
    }

    public Integer getBwtype() {
        return bwtype;
    }

    public void setBwtype(Integer bwtype) {
        this.bwtype = bwtype;
    }

    public String getBanwenbianhao() {
        return banwenbianhao;
    }

    public void setBanwenbianhao(String banwenbianhao) {
        this.banwenbianhao = banwenbianhao;
    }

    public String getLaiwendanwei() {
        return laiwendanwei;
    }

    public void setLaiwendanwei(String laiwendanwei) {
        this.laiwendanwei = laiwendanwei;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public Timestamp getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Timestamp createdatetime) {
        this.createdatetime = createdatetime;
    }

    public Timestamp getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Timestamp lastmodified) {
        this.lastmodified = lastmodified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatorGUID() {
        return creatorGUID;
    }

    public void setCreatorGUID(String creatorGUID) {
        this.creatorGUID = creatorGUID;
    }

    public String getCreatorname() {
        return creatorname;
    }

    public void setCreatorname(String creatorname) {
        this.creatorname = creatorname;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDepartmentguid() {
        return departmentguid;
    }

    public void setDepartmentguid(String departmentguid) {
        this.departmentguid = departmentguid;
    }

    public String getBureauname() {
        return bureauname;
    }

    public void setBureauname(String bureauname) {
        this.bureauname = bureauname;
    }

    public List<DeptBaseInfo> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DeptBaseInfo> departments) {
        this.departments = departments;
    }
}