package com.huasisoft.search.core.strategy.highLight;

import com.huasisoft.search.core.constant.HighLightFieldConstant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-17.
 * @Time 16:22
 * @Description 全部查询高亮策略类
 * @Version 2.0.0
 */
public class SearchHighlightFieldStrategy implements HighlightFieldStrategy {
    @Override
    public String[] getHighlightFields() {
        String[] highlightFields = {HighLightFieldConstant.TITLE,
                HighLightFieldConstant.DATA,
                HighLightFieldConstant.LAIWENDANWEI,
                HighLightFieldConstant.BANWENBIANHAO
        };
        return highlightFields;
    }
}
