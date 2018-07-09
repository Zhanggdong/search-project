package com.huasisoft.search.admin.mock.index;

import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.service.proxy.IndexManagerProxyService;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-07-02.
 * @Time 9:42
 * @Description TODO
 * @Version 2.0.0
 */
public class IndexManagerProxyServiceMock implements IndexManagerProxyService{
    @Override
    public boolean existsIndexType(IndexManagerBeans beans) {
        return false;
    }

    @Override
    public boolean createIndex(IndexManagerBeans beans) {
        return false;
    }

    @Override
    public boolean deleteIndex(IndexManagerBeans beans) {
        return false;
    }

    @Override
    public boolean deleteIndexType(IndexManagerBeans beans) {
        return false;
    }

    @Override
    public boolean setMapping(IndexManagerBeans beans) throws Exception {
        return false;
    }

    @Override
    public boolean initIndexData(IndexManagerBeans beans) throws Exception {
        return false;
    }

    @Override
    public boolean initIndexData(int from, int to, IndexManagerBeans beans) throws Exception {
        return false;
    }

    @Override
    public boolean stop(IndexManagerBeans beans) throws Exception {
        return false;
    }

    @Override
    public int count(IndexManagerBeans beans) throws Exception {
        return 0;
    }


}
