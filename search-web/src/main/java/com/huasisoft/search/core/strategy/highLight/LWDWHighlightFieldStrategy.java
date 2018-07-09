package com.huasisoft.search.core.strategy.highLight;

import com.huasisoft.search.core.constant.HighLightFieldConstant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-17.
 * @Time 16:21
 * @Description 来文单位高亮策略类
 * @Version 2.0.0
 */
public class LWDWHighlightFieldStrategy implements HighlightFieldStrategy {
    @Override
    public String[] getHighlightFields() {
        String[] highlightFields = {HighLightFieldConstant.LAIWENDANWEI};
        return highlightFields;
    }
}
