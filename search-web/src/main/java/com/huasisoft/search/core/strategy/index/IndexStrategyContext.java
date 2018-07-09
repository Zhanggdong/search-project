package com.huasisoft.search.core.strategy.index;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-08.
 * @Time 14:57
 * @Description TODO
 * @Version 2.0.0
 */
public class IndexStrategyContext {
    private IndexStrategy strategy;

    public IndexStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IndexStrategy strategy) {
        this.strategy = strategy;
    }

    public String[] getIndexs(String searchType) {
        strategy = IndexStrategyFactory.getInstance().creator(searchType);
        return strategy.getIndexs();
    }
}
