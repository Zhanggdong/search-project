package com.huasisoft.search.admin.serviceImpl;

import com.huasisoft.search.admin.model.IndexManagerBeans;
import com.huasisoft.search.core.model.es.ESBaseData;
import org.springframework.stereotype.Service;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 14:14
 * @Description TODO
 * @Version 2.0.0
 */
@Service("commentIndexManagerService")
public class CommentIndexManagerServiceImpl extends AbstractIndexManagerServiceImpl {
    @Override
    public boolean initIndexData(IndexManagerBeans beans) throws Exception {
        return false;
    }
}
