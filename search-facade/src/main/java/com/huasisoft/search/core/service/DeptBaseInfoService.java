package com.huasisoft.search.core.service;

import com.huasisoft.search.core.model.db.DeptBaseInfo;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-08.
 * @Time 15:08
 * @Description TODO
 * @Version 2.0.0
 */
public interface DeptBaseInfoService {
    List<DeptBaseInfo> selectByWorkflowinstanceGUID(String workflowInstanceGUID);
}
