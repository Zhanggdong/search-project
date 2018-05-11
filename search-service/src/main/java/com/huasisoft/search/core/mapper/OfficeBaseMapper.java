package com.huasisoft.search.core.mapper;

import com.huasisoft.search.core.model.db.OfficeBase;

import java.util.List;

public interface OfficeBaseMapper {
    int count();
    List<OfficeBase> selectByPage();
}