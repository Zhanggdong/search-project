package com.huasisoft.search.admin.index.mapper;

import com.huasisoft.search.admin.index.model.AttachmentBase;

import java.util.List;
import java.util.Map;

public interface AttachmentBaseMapper {
    List<AttachmentBase> selectByPage(Map<String,Integer> map);

    int count();

    List<AttachmentBase> selectByPageInfo(Map<String,Integer> map);

    List<AttachmentBase> selectByPageFromTo(Map<String,Integer> map);

}