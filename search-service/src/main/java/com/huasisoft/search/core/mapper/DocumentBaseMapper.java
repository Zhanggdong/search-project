package com.huasisoft.search.core.mapper;

import com.huasisoft.search.core.model.db.DocumentBase;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DocumentBaseMapper {
    int count();

    List<DocumentBase> selectByPage();
}