package com.huasisoft.search.admin.dict.service;

import com.huasisoft.search.admin.dict.execption.ExtendWordException;
import com.huasisoft.search.admin.dict.model.ExtendWord;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 9:37
 * @Description 扩展词接口
 * @Version 2.0.0
 */
public interface ExtendWordService {
    public boolean save(ExtendWord extendWord) throws ExtendWordException;

    public boolean removeByContent(String id) throws ExtendWordException;

    public boolean removeByContentList(List<String> content) throws ExtendWordException;

    public List<ExtendWord> query(int pageNum,int pageSize)throws ExtendWordException;
}
