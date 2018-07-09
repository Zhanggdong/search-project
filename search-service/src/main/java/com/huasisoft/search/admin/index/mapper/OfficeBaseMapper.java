package com.huasisoft.search.admin.index.mapper;

import com.huasisoft.search.admin.index.model.OfficeBase;

import java.util.List;
import java.util.Map;

public interface OfficeBaseMapper {
    int count();
    List<OfficeBase> selectByPage(Map<String,Integer> map);
    List<OfficeBase> selectByPageInfo(Map<String,Integer> map);
    List<OfficeBase> selectByPageFromTo(Map<String,Integer> map);
}