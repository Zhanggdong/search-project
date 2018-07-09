package com.huasisoft.search.core.strategy.indexType;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 18:11
 * @Description 索引类型Context
 * @Version 2.0.0
 */
public class IndexTypeStrategyContext {
    private IndexTypeStrategy strategy;

    public IndexTypeStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IndexTypeStrategy strategy) {
        this.strategy = strategy;
    }

    public String[] getIndexType(String searchType) {
        strategy = IndexTypeStrategyFactory.getInstance().creator(searchType);
        return strategy.getIndexType();
    }
}
