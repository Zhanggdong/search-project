package com.huasisoft.search.admin.dict.service;

import com.huasisoft.search.admin.dict.execption.SynonymWordException;
import com.huasisoft.search.admin.dict.model.SynonymWord;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 9:36
 * @Description 同义词接口
 * @Version 2.0.0
 */
public interface AntonymWordService {

    public boolean save(SynonymWord synonymWord) throws SynonymWordException;

    public boolean removeByContent(String content) throws SynonymWordException;

    public boolean remove(List<String> ids) throws SynonymWordException;

    public List<SynonymWord> query(int pageNum, int pageSize) throws SynonymWordException;
}
