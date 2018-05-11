package com.huasisoft.search.core.mapper;

import com.huasisoft.search.core.model.db.AttachmentBase;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttachmentBaseMapper {
    List<AttachmentBase> selectByPage();

    int count();
}