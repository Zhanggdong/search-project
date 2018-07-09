package com.huasisoft.search.admin.index.model;

import com.huasisoft.search.admin.index.constant.AbstractIndexRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 9:39
 * @Description 全文检索 index 索引数据
 * @Version 2.0.0
 */
public class ESBaseData extends AbstractIndexRequest implements Serializable{
    private static final long serialVersionUID = 7142936141593939499L;

    private String id;

    private String guid;

    private String title;

    private Integer bwtype;

    private String banwenbianhao;

    private String laiwendanwei;

    private String createdatetime;

    private String url;

    private String creatorGUID;

    private String creatorname;

    private String dn;

    private Integer type;

    private String bureauname;

    private String search;
    // 办件所经手的部门
    private List<ESDeptBaseInfo> departments;
    /**
     * 附件内容、正文内容、意见内容数据
     */
    private String data;


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

    public String getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(String createdatetime) {
        this.createdatetime = createdatetime;
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

    public String getBureauname() {
        return bureauname;
    }

    public void setBureauname(String bureauname) {
        this.bureauname = bureauname;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<ESDeptBaseInfo> getDepartments() {
        return departments;
    }

    public void setDepartments(List<ESDeptBaseInfo> departments) {
        this.departments = departments;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ESBaseData that = (ESBaseData) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (guid != null ? !guid.equals(that.guid) : that.guid != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (bwtype != null ? !bwtype.equals(that.bwtype) : that.bwtype != null) return false;
        if (banwenbianhao != null ? !banwenbianhao.equals(that.banwenbianhao) : that.banwenbianhao != null)
            return false;
        if (laiwendanwei != null ? !laiwendanwei.equals(that.laiwendanwei) : that.laiwendanwei != null) return false;
        if (createdatetime != null ? !createdatetime.equals(that.createdatetime) : that.createdatetime != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (creatorGUID != null ? !creatorGUID.equals(that.creatorGUID) : that.creatorGUID != null) return false;
        if (creatorname != null ? !creatorname.equals(that.creatorname) : that.creatorname != null) return false;
        if (dn != null ? !dn.equals(that.dn) : that.dn != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (bureauname != null ? !bureauname.equals(that.bureauname) : that.bureauname != null) return false;
        if (search != null ? !search.equals(that.search) : that.search != null) return false;
        if (departments != null ? !departments.equals(that.departments) : that.departments != null) return false;
        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (guid != null ? guid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (bwtype != null ? bwtype.hashCode() : 0);
        result = 31 * result + (banwenbianhao != null ? banwenbianhao.hashCode() : 0);
        result = 31 * result + (laiwendanwei != null ? laiwendanwei.hashCode() : 0);
        result = 31 * result + (createdatetime != null ? createdatetime.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (creatorGUID != null ? creatorGUID.hashCode() : 0);
        result = 31 * result + (creatorname != null ? creatorname.hashCode() : 0);
        result = 31 * result + (dn != null ? dn.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (bureauname != null ? bureauname.hashCode() : 0);
        result = 31 * result + (search != null ? search.hashCode() : 0);
        result = 31 * result + (departments != null ? departments.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
