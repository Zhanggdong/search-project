package com.huasisoft.search.core.mapper;

import com.huasisoft.search.core.model.db.WorkflowCommentBase;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkflowCommentBaseMapper {
    int count();

    List<WorkflowCommentBase> selectByPage();
}