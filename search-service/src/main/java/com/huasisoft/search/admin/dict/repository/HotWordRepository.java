package com.huasisoft.search.admin.dict.repository;

import com.huasisoft.search.admin.dict.model.ExtendWord;
import com.huasisoft.search.admin.dict.model.HotWord;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 14:45
 * @Description 同义词Redis操作 Repository
 * @Version 2.0.0
 */
@Repository
public class HotWordRepository {

    public HotWord save(HotWord hotWord) throws Exception{
        return null;
    }

    public List<ExtendWord> query(int pageNum,int pageSize) throws Exception{
        return null;
    }

    public boolean removeByContent(String content) throws Exception{
        return removeByContent(Arrays.asList(content));
    }

    public boolean removeByContent(List<String> contents) throws Exception{
        return false;
    }

}
