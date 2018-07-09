package com.huasisoft.search.core.strategy.indexType;

import com.huasisoft.search.core.constant.IndexTypeConstant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 18:20
 * @Description 全部查询索引数据库处理类
 * @Version 2.0.0
 */
public class SearchIndexTypeStrategy implements IndexTypeStrategy {
    @Override
    public String[] getIndexType() {
        String[] indexTypes = {IndexTypeConstant.OFFICE+IndexTypeConstant.DOCUMENT+IndexTypeConstant.COMMENT+IndexTypeConstant.ATTACHMENT};
        return indexTypes;
    }
}
