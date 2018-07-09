package com.huasisoft.search.core.strategy.highLight;

import com.huasisoft.search.core.constant.HighLightFieldConstant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-17.
 * @Time 16:14
 * @Description 标题查询高亮策略类
 * @Version 2.0.0
 */
public class TitleHighlightFieldStrategy implements HighlightFieldStrategy {

    @Override
    public String[] getHighlightFields() {
        String[] highlightFields = {HighLightFieldConstant.TITLE,HighLightFieldConstant.DATA};
        return highlightFields;
    }
}
