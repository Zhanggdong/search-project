package com.huasisoft.search.core.model;

import com.huasisoft.search.admin.index.constant.DBIndexType;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 16:11
 * @Description 全文检索Vo对象
 * @Version 2.0.0
 */
public class SearchDocument implements Serializable{

    private static final long serialVersionUID = -1870827320401972619L;
    /**
     *  文档对象ID
     */
    private String id;
    /**
     *  公文实例：业务使用（打开详情需要的参数）
     */
    private String guid;
    /**
     *  显示标题
     */
    private String title;
    /**
     *  办文类型
     */
    private Integer bwtype;
    /**
     *  办文编号
     */
    private String banwenbianhao;
    /**
     *  来文单位
     */
    private String laiwendanwei;
    /**
     *  创建时间：时间范围过滤使用
     */
    private String createdate;
    /**
     *  创建时间
     */
    private String createdatetime;
    /**
     *  创建人ID
     */
    private String creatorGUID;
    /**
     *  创建人
     */
    private String creatorname;
    /**
     *  部门
     */
    private String dn;
    /**
     *  索引类型:详情查看索引类型常量类
     *  @see DBIndexType
     */
    private Integer type;
    /**
     *  局办名称
     */
    private String bureauname;

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

    public String getCreatedatetime() {
        return createdatetime;
    }

    public void setCreatedatetime(String createdatetime) {
        this.createdatetime = createdatetime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchDocument that = (SearchDocument) o;

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
        if (creatorGUID != null ? !creatorGUID.equals(that.creatorGUID) : that.creatorGUID != null) return false;
        if (creatorname != null ? !creatorname.equals(that.creatorname) : that.creatorname != null) return false;
        if (dn != null ? !dn.equals(that.dn) : that.dn != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return bureauname != null ? bureauname.equals(that.bureauname) : that.bureauname == null;
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
        result = 31 * result + (creatorGUID != null ? creatorGUID.hashCode() : 0);
        result = 31 * result + (creatorname != null ? creatorname.hashCode() : 0);
        result = 31 * result + (dn != null ? dn.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (bureauname != null ? bureauname.hashCode() : 0);
        return result;
    }
}
