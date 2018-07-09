package com.huasisoft.search.admin.dict.repository;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.admin.dict.constant.DictFieldName;
import com.huasisoft.search.admin.dict.model.StopWord;
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
public class StopWordRepository {

    public StopWord save(StopWord stopWord) throws Exception{
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        stopWord.setCreateTime(timestamp);
        stopWord.setUpdateTime(timestamp);
        stopWord.setDeleted(0);
        String data = JSONObject.toJSONString(stopWord);
        // 保存到Redis
        JedisUtils.getInstance().LISTS.lpush(DictFieldName.STOP_WORD.getKey(),data);
        return stopWord;
    }

    public List<StopWord> query(int pageNum,int pageSize) throws Exception{
        long start = (pageNum-1)*pageSize;
        long end = pageSize*pageSize;
        List<String> stopWordStrList = JedisUtils.getInstance().LISTS.lrange(DictFieldName.STOP_WORD.getKey(),start,end);
        List<StopWord> stopWords = null;
        for (String stopWordStr:stopWordStrList){
            StopWord extendWord = JSONObject.parseObject(stopWordStr,StopWord.class);
            stopWords.add(extendWord);
        }
        return stopWords;
    }

    public boolean removeByContent(String content) throws Exception{
        return removeByContent(Arrays.asList(content));
    }

    public boolean removeByContent(List<String> contents) throws Exception{
        boolean removed = false;
        List<String> stopWordStrList = JedisUtils.getInstance().LISTS.lrange(DictFieldName.STOP_WORD.getKey(),0,-1);
        for (String stopWordStr:stopWordStrList){
            StopWord stopWord = JSONObject.parseObject(stopWordStr,StopWord.class);
            if (contents.contains(stopWord.getWord())){
                JedisUtils.getInstance().LISTS.lrem(DictFieldName.STOP_WORD.getKey(),0,stopWordStr);
                removed = true;
            }
        }
        return removed;
    }

}
