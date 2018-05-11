package com.huasisoft.search.core.model.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:32
 * @Description 附件基本信息视图对象
 * @Version 2.0.0
 */
public class AttachmentBase implements Serializable{

    private static final long serialVersionUID = 3584072700166564962L;
    private String ID;

    private String GUID;

    private String title;

    private String fjname;

    private Integer bwtype;

    private String banwenbianhao;

    private String laiwendanwei;

    private String createdate;

    private Date createdatetime;

    private Date lastmodified;

    private String url;

    private String creatorGUID;

    private String creatorname;

    private String dn;

    private Integer type;

    private String departmentguid;

    private String bureauname;

    // 办件所经手的部门
    private List<DeptBaseInfo> departments;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID == null ? null : ID.trim();
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID == null ? null : GUID.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFjname() {
        return fjname;
    }

    public void setFjname(String fjname) {
        this.fjname = fjname == null ? null : fjname.trim();
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
        this.banwenbianhao = banwenbianhao == null ? null : banwenbianhao.trim();
    }

    public String getLaiwendanwei() {
        return laiwendanwei;
    }

    public void setLaiwendanwei(String laiwendanwei) {
        this.laiwendanwei = laiwendanwei == null ? null : laiwendanwei.trim();
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate == null ? null : createdate.trim();
    }

    public Date getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCreatorGUID() {
        return creatorGUID;
    }

    public void setCreatorGUID(String creatorGUID) {
        this.creatorGUID = creatorGUID == null ? null : creatorGUID.trim();
    }

    public String getCreatorname() {
        return creatorname;
    }

    public void setCreatorname(String creatorname) {
        this.creatorname = creatorname == null ? null : creatorname.trim();
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn == null ? null : dn.trim();
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
        this.departmentguid = departmentguid == null ? null : departmentguid.trim();
    }

    public List<DeptBaseInfo> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DeptBaseInfo> departments) {
        this.departments = departments;
    }

    public String getBureauname() {
        return bureauname;
    }

    public void setBureauname(String bureauname) {
        this.bureauname = bureauname == null ? null : bureauname.trim();
    }
}