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
public class AttachmentTest implements Serializable{
    private static final long serialVersionUID = 1334675419671654770L;
    private String ID;
    private String GUID;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
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
}
