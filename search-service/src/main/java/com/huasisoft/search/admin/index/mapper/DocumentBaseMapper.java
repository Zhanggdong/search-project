package com.huasisoft.search.admin.index.mapper;

import com.huasisoft.search.admin.index.model.DocumentBase;

import java.util.List;
import java.util.Map;

public interface DocumentBaseMapper {
    int count();

    List<DocumentBase> selectByPage(Map<String,Integer> map);

    List<DocumentBase> selectByPageFromTo(Map<String,Integer> map);
}