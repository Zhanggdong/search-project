package com.huasisoft.search.core.strategy.highLight;

import com.huasisoft.search.core.constant.SearchTypeEnum;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-17.
 * @Time 16:26
 * @Description TODO
 * @Version 2.0.0
 */
public class HighlightFieldStrategyContext {

    private HighlightFieldStrategy strategy;

    public String[] getHighlightFields(String searchType){
        if ("null".equals(searchType)){
            searchType = SearchTypeEnum.SEARCH.getSearchType();
        }
        strategy = HighlightFieldStrategyFactory.getInstance().creator(searchType);
        return strategy.getHighlightFields();
    }
    public HighlightFieldStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(HighlightFieldStrategy strategy) {
        this.strategy = strategy;
    }

}
