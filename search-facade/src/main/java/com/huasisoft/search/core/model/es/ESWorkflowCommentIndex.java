package com.huasisoft.search.core.model.es;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 14:37
 * @Description 公文意见索引
 * @Version 2.0.0
 */
public class ESWorkflowCommentIndex extends ESBaseData implements Serializable {
    private static final long serialVersionUID = -4354809370619079497L;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
