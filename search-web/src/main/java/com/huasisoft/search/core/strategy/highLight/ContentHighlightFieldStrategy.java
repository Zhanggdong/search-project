package com.huasisoft.search.core.strategy.highLight;

import com.huasisoft.search.core.constant.HighLightFieldConstant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-17.
 * @Time 16:19
 * @Description 查询内容的高亮策略
 * @Version 2.0.0
 */
public class ContentHighlightFieldStrategy implements HighlightFieldStrategy{
    @Override
    public String[] getHighlightFields() {
        String[] highlightFields = {HighLightFieldConstant.TITLE,HighLightFieldConstant.DATA};
        return highlightFields;
    }
}
