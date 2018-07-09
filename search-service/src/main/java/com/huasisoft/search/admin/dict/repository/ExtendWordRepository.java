package com.huasisoft.search.admin.dict.repository;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.admin.dict.constant.DictFieldName;
import com.huasisoft.search.admin.dict.model.ExtendWord;
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
public class ExtendWordRepository {

    public ExtendWord save(ExtendWord extendWord) throws Exception{
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        extendWord.setCreateTime(timestamp);
        extendWord.setUpdateTime(timestamp);
        extendWord.setDeleted(0);
        String data = JSONObject.toJSONString(extendWord);
        // 保存到Redis
        JedisUtils.getInstance().LISTS.lpush(DictFieldName.EXTEND_WORD.getKey(),data);
        return extendWord;
    }

    public List<ExtendWord> query(int pageNum,int pageSize) throws Exception{
        long start = (pageNum-1)*pageSize;
        long end = pageSize*pageSize;
        List<String> extendWordStrList = JedisUtils.getInstance().LISTS.lrange(DictFieldName.EXTEND_WORD.getKey(),start,end);
        List<ExtendWord> extendWords = null;
        for (String extendWordStr:extendWordStrList){
            ExtendWord extendWord = JSONObject.parseObject(extendWordStr,ExtendWord.class);
            extendWords.add(extendWord);
        }
        return extendWords;
    }

    public boolean removeByContent(String content) throws Exception{
        return removeByContent(Arrays.asList(content));
    }

    public boolean removeByContent(List<String> contents) throws Exception{
        boolean removed = false;
        List<String> extendWordStrList = JedisUtils.getInstance().LISTS.lrange(DictFieldName.EXTEND_WORD.getKey(),0,-1);
        for (String extendWordStr:extendWordStrList){
            ExtendWord extendWord = JSONObject.parseObject(extendWordStr,ExtendWord.class);
            if (contents.contains(extendWord.getWord())){
                JedisUtils.getInstance().LISTS.lrem(DictFieldName.EXTEND_WORD.getKey(),0,extendWordStr);
                removed = true;
            }
        }
        return removed;
    }

}
