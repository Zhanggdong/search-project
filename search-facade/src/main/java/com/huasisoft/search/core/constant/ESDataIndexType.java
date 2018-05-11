package com.huasisoft.search.core.constant;


import com.huasisoft.search.core.model.es.*;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 15:24
 * @Description 索引数据对象
 * @Version 2.0.0
 */
public enum ESDataIndexType {
    ES_ATTACHMENT_INDEX(new ESAttachmentIndex()),
    ES_DOCUMENT_INDEX(new ESDocumentIndex()),
    ES_COMMENT_INDEX(new ESWorkflowCommentIndex()),
    ES_OFFICE_INDEX(new ESOfficeIndex());
    private ESBaseData indexData;

    ESDataIndexType(ESBaseData indexData) {
        this.indexData = indexData;
    }

    public ESBaseData get(){
        return  this.indexData;
    }
}
