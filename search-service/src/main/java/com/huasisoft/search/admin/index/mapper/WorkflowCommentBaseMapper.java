package com.huasisoft.search.admin.index.mapper;

import com.huasisoft.search.admin.index.model.WorkflowCommentBase;

import java.util.List;
import java.util.Map;

public interface WorkflowCommentBaseMapper {
    int count();

    List<WorkflowCommentBase> selectByPage(Map<String,Integer> map);

    List<WorkflowCommentBase> selectByPageInfo(Map<String,Integer> map);

    List<WorkflowCommentBase> selectByPageFromTo(Map<String,Integer> map);
}