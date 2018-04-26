package com.huasisoft.search.demo.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-25.
 * @Time 15:08
 * @Description 附件文档索引测试
 * @Version 2.0.0
 */
public class Attachment implements Serializable{
    private static final long serialVersionUID = 1334675419671654770L;
    private String id;
    private String guid;
    private String title;
    private int bwtype;
    private String banwenbianhao;
    private String laiwendanwei;
    private String createdate;
    private String createdateTime;
    private String url;
    private String creatorGUID;
    private String creatorname;
    private String dn;
    private int type;
    private String departmentGUID;
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

    public int getBwtype() {
        return bwtype;
    }

    public void setBwtype(int bwtype) {
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

    public String getCreatedateTime() {
        return createdateTime;
    }

    public void setCreatedateTime(String createdateTime) {
        this.createdateTime = createdateTime;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDepartmentGUID() {
        return departmentGUID;
    }

    public void setDepartmentGUID(String departmentGUID) {
        this.departmentGUID = departmentGUID;
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

        Attachment that = (Attachment) o;

        if (bwtype != that.bwtype) return false;
        if (type != that.type) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (guid != null ? !guid.equals(that.guid) : that.guid != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (banwenbianhao != null ? !banwenbianhao.equals(that.banwenbianhao) : that.banwenbianhao != null)
            return false;
        if (laiwendanwei != null ? !laiwendanwei.equals(that.laiwendanwei) : that.laiwendanwei != null) return false;
        if (createdate != null ? !createdate.equals(that.createdate) : that.createdate != null) return false;
        if (createdateTime != null ? !createdateTime.equals(that.createdateTime) : that.createdateTime != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (creatorGUID != null ? !creatorGUID.equals(that.creatorGUID) : that.creatorGUID != null) return false;
        if (creatorname != null ? !creatorname.equals(that.creatorname) : that.creatorname != null) return false;
        if (dn != null ? !dn.equals(that.dn) : that.dn != null) return false;
        if (departmentGUID != null ? !departmentGUID.equals(that.departmentGUID) : that.departmentGUID != null)
            return false;
        return data != null ? data.equals(that.data) : that.data == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (guid != null ? guid.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + bwtype;
        result = 31 * result + (banwenbianhao != null ? banwenbianhao.hashCode() : 0);
        result = 31 * result + (laiwendanwei != null ? laiwendanwei.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        result = 31 * result + (createdateTime != null ? createdateTime.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (creatorGUID != null ? creatorGUID.hashCode() : 0);
        result = 31 * result + (creatorname != null ? creatorname.hashCode() : 0);
        result = 31 * result + (dn != null ? dn.hashCode() : 0);
        result = 31 * result + type;
        result = 31 * result + (departmentGUID != null ? departmentGUID.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
