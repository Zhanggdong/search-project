package com.huasisoft.search.core.strategy.indexType;

import com.huasisoft.search.core.constant.IndexTypeConstant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-21.
 * @Time 14:17
 * @Description 附件查询索引数据库处理类
 * @Version 2.0.0
 */
public class AttachmentIndexTypeStrategy implements IndexTypeStrategy{
    @Override
    public String[] getIndexType() {
        return IndexTypeConstant.ATTACHMENT.split(",");
    }
}
