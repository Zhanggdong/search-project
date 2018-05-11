package com.huasisoft.search.core.model.es;

import com.huasisoft.search.core.model.db.DeptBaseInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 9:39
 * @Description 全文检索 es 索引数据
 * @Version 2.0.0
 */
public abstract class ESBaseData implements Serializable{
    private static final long serialVersionUID = 7142936141593939499L;
    private String ID;

    private String GUID;

    private String title;

    private Integer bwtype;

    private String banwenbianhao;

    private String laiwendanwei;

    private String createdate;

    private String createdatetime;

    private String url;

    private String creatorGUID;

    private String creatorname;

    private String dn;

    private Integer type;

    private String bureauname;
    // 办件所经手的部门
    private List<DeptBaseInfo> departments;
    /**
     * 附件内容、正文内容、意见内容数据
     */
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

    public List<DeptBaseInfo> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DeptBaseInfo> departments) {
        this.departments = departments;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


}
