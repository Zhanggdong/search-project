package com.huasisoft.search.core.strategy.index;

import com.huasisoft.search.core.constant.IndexConstant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-08.
 * @Time 15:05
 * @Description TODO
 * @Version 2.0.0
 */
public class SearchIndexStrategy implements IndexStrategy {
    @Override
    public String[] getIndexs() {
        return IndexConstant.All.split(",");
    }
}
