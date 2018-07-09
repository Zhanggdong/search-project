package com.huasisoft.search.admin.dict.repository;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.admin.dict.constant.DictFieldName;
import com.huasisoft.search.admin.dict.model.SynonymWord;
import com.huasisoft.search.common.util.JedisUtils;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
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
public class SynonymWordRepository {

    public SynonymWord save(SynonymWord synonymWord) throws Exception{
        //long len = JedisUtils.getInstance().LISTS.llen(DictFieldName.SYNONYM_WORD.getKey());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        synonymWord.setCreateTime(timestamp);
        synonymWord.setUpdateTime(timestamp);
        synonymWord.setDeleted(0);
        String data = JSONObject.toJSONString(synonymWord);
        // 保存到Redis
        JedisUtils.getInstance().LISTS.lpush(DictFieldName.SYNONYM_WORD.getKey(),data);
        return synonymWord;
    }

    public List<SynonymWord> query(int pageNum,int pageSize) throws Exception{
        long start = (pageNum-1)*pageSize;
        long end = pageSize*pageSize;
        List<String> synonymWordStrList = JedisUtils.getInstance().LISTS.lrange(DictFieldName.SYNONYM_WORD.getKey(),start,end);
        List<SynonymWord> synonymWordList = null;
        for (String synonymWordStr:synonymWordStrList){
            SynonymWord synonymWord = JSONObject.parseObject(synonymWordStr,SynonymWord.class);
            synonymWordList.add(synonymWord);
        }
        return synonymWordList;
    }

    public boolean removeByContent(String content) throws Exception{
        return removeByContent(Arrays.asList(content));
    }

    public boolean removeByContent(List<String> contents) throws Exception{
        boolean removed = false;
        List<String> synonymWordStrList = JedisUtils.getInstance().LISTS.lrange(DictFieldName.SYNONYM_WORD.getKey(),0,-1);
        for (String synonymWordStr:synonymWordStrList){
            SynonymWord synonymWord = JSONObject.parseObject(synonymWordStr,SynonymWord.class);
            if (contents.contains(synonymWord.getWord())){
                JedisUtils.getInstance().LISTS.lrem(DictFieldName.SYNONYM_WORD.getKey(),0,synonymWordStr);
                removed = true;
            }
        }
        return removed;
    }

}
