package com.huasisoft.search.core.mapper;

import com.huasisoft.search.core.model.db.DeptBaseInfo;
import java.util.List;

public interface DeptBaseInfoMapper {

    List<DeptBaseInfo> selectByWorkflowinstanceGUID(String workflowInstanceGUID);

}