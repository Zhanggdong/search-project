package com.huasisoft.search.admin.index.mapper;

import com.huasisoft.search.admin.index.model.DeptBaseInfo;
import java.util.List;

public interface DeptBaseInfoMapper {

    List<DeptBaseInfo> selectByWorkflowinstanceGUID(String workflowInstanceGUID);

}