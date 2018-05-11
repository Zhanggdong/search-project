package com.huasisoft.search.core.serviceImpl;

import com.huasisoft.search.core.mapper.DeptBaseInfoMapper;
import com.huasisoft.search.core.model.db.DeptBaseInfo;
import com.huasisoft.search.core.service.DeptBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-08.
 * @Time 15:20
 * @Description TODO
 * @Version 2.0.0
 */
@Service
public class DeptBaseInfoServiceImpl implements DeptBaseInfoService {
    @Autowired
    private DeptBaseInfoMapper deptBaseInfoMapper;
    @Override
    public List<DeptBaseInfo> selectByWorkflowinstanceGUID(String workflowInstanceGUID) {
        return deptBaseInfoMapper.selectByWorkflowinstanceGUID(workflowInstanceGUID);
    }
}
