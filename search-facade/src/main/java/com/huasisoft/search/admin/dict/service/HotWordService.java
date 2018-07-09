package com.huasisoft.search.admin.dict.service;

import com.huasisoft.search.admin.dict.model.HotWord;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 9:37
 * @Description 热词接口
 * @Version 2.0.0
 */
public interface HotWordService {
    public boolean save(HotWord hotWord) throws Exception;

    public boolean removeById(String id) throws Exception;

    public boolean remove(List<String> ids) throws Exception;

    public List<HotWord> query(int pageNum,int pageSize)throws Exception;
}
