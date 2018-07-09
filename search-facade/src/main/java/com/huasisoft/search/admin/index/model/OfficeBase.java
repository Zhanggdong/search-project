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
 * @Description 公文基本信息视图对象
 * @Version 2.0.0
 */
public class OfficeBase extends AbstractIndexRequest implements Serializable{

    private static final long serialVersionUID = 5481983868441033201L;

    private String id;

    private String guid;

    private String title;

    private Integer bwtype;

    private String banwenbianhao;

    private String laiwendanwei;

    private String createdate;

    private Timestamp createdatetime;

    private Integer instancedeleted;

    private String creatorGUID;

    private String creatorname;

    private String dn;

    private String bureauGUID;

    private String bureauname;

    // 办件所经手的部门
    private List<DeptBaseInfo> departments;

    private Integer type;

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

    public Integer getInstancedeleted() {
        return instancedeleted;
    }

    public void setInstancedeleted(Integer instancedeleted) {
        this.instancedeleted = instancedeleted;
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

    public String getBureauGUID() {
        return bureauGUID;
    }

    public void setBureauGUID(String bureauGUID) {
        this.bureauGUID = bureauGUID;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfficeBase that = (OfficeBase) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (guid != null ? !guid.equals(that.guid) : that.guid != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (bwtype != null ? !bwtype.equals(that.bwtype) : that.bwtype != null) return false;
        if (banwenbianhao != null ? !banwenbianhao.equals(that.banwenbianhao) : that.banwenbianhao != null)
            return false;
        if (laiwendanwei != null ? !laiwendanwei.equals(that.laiwendanwei) : that.laiwendanwei != null) return false;
        if (createdate != null ? !createdate.equals(that.createdate) : that.createdate != null) return false;
        if (createdatetime != null ? !createdatetime.equals(that.createdatetime) : that.createdatetime != null)
            return false;
        if (instancedeleted != null ? !instancedeleted.equals(that.instancedeleted) : that.instancedeleted != null)
            return false;
        if (creatorGUID != null ? !creatorGUID.equals(that.creatorGUID) : that.creatorGUID != null) return false;
        if (creatorname != null ? !creatorname.equals(that.creatorname) : that.creatorname != null) return false;
        if (dn != null ? !dn.equals(that.dn) : that.dn != null) return false;
        if (bureauGUID != null ? !bureauGUID.equals(that.bureauGUID) : that.bureauGUID != null) return false;
        if (bureauname != null ? !bureauname.equals(that.bureauname) : that.bureauname != null) return false;
        if (departments != null ? !departments.equals(that.departments) : that.departments != null) return false;
        return type != null ? type.equals(that.type) : that.type == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (guid != null ? guid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (bwtype != null ? bwtype.hashCode() : 0);
        result = 31 * result + (banwenbianhao != null ? banwenbianhao.hashCode() : 0);
        result = 31 * result + (laiwendanwei != null ? laiwendanwei.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        result = 31 * result + (createdatetime != null ? createdatetime.hashCode() : 0);
        result = 31 * result + (instancedeleted != null ? instancedeleted.hashCode() : 0);
        result = 31 * result + (creatorGUID != null ? creatorGUID.hashCode() : 0);
        result = 31 * result + (creatorname != null ? creatorname.hashCode() : 0);
        result = 31 * result + (dn != null ? dn.hashCode() : 0);
        result = 31 * result + (bureauGUID != null ? bureauGUID.hashCode() : 0);
        result = 31 * result + (bureauname != null ? bureauname.hashCode() : 0);
        result = 31 * result + (departments != null ? departments.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}