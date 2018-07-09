package com.huasisoft.search.admin.mock.dict;

import com.huasisoft.search.admin.dict.execption.SynonymWordException;
import com.huasisoft.search.admin.dict.model.SynonymWord;
import com.huasisoft.search.admin.dict.service.AntonymWordService;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-07-02.
 * @Time 9:35
 * @Description 同义词接口mock
 * @Version 2.0.0
 */
public class AntonymWordServiceMock implements AntonymWordService {
    @Override
    public boolean save(SynonymWord synonymWord) throws SynonymWordException {
        return false;
    }

    @Override
    public boolean removeByContent(String content) throws SynonymWordException {
        return false;
    }

    @Override
    public boolean remove(List<String> ids) throws SynonymWordException {
        return false;
    }

    @Override
    public List<SynonymWord> query(int pageNum, int pageSize) throws SynonymWordException {
        return null;
    }
}
