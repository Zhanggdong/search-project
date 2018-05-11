package com.huasisoft.search.core.model.es;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 14:36
 * @Description 附件索引
 * @Version 2.0.0
 */
public class ESAttachmentIndex extends ESBaseData implements Serializable {
    private static final long serialVersionUID = -1059608635314840072L;

    private String fjname;

    public String getFjname() {
        return fjname;
    }

    public void setFjname(String fjname) {
        this.fjname = fjname;
    }
}
