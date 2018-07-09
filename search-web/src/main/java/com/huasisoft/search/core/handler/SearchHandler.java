package com.huasisoft.search.core.handler;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.core.model.SearchDocument;
import com.huasisoft.search.core.query.search.HSHit;
import com.huasisoft.search.core.query.search.HSHitContext;
import com.huasisoft.search.core.query.search.HSSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 16:31
 * @Description 查询结果处理类
 * @Version 2.0.0
 */
public class SearchHandler {
    private static final Logger logger = LoggerFactory.getLogger(SearchHandler.class);
    private HSSearchResponse response;
    private List<SearchDocument> documents;

    List<Map<String,Object>> highlightingList;

    public SearchHandler(HSSearchResponse response) {
        this.response = response;
    }

    /**
     * 处理查询结果<br/>
     *
     */
    public void handler(){
        documents = new ArrayList<>();
        HSHitContext hitContext =response.getHits();
        List<HSHit> hits = hitContext.getHits();
        highlightingList = new ArrayList<>();
        for (HSHit hit:hits){
            String sourceJson =  JSONObject.toJSONString(hit.get_source());
            SearchDocument document = JSONObject.parseObject(sourceJson,SearchDocument.class);
            Map<String,Object> highlightMap = hit.getHighlight();
            if (highlightMap!=null){
                highlightingList.add(highlightMap);
                Class<?> clazz = document.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field:fields){
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    if (highlightMap.containsKey(fieldName)&&highlightMap.get(fieldName)!=null){
                        try {
                            field.set(document,highlightMap.get(fieldName));
                        } catch (IllegalAccessException e) {
                            logger.error("属性{}高亮内容设置失败!",fieldName);
                        }
                    }
                }
            }
            documents.add(document);
        }
    }

    /**
     * 返回高亮信息
     * @return
     */
    public List<Map<String,Object>> getHighlightingMap(){
        return highlightingList;
    }

    /**
     * 返回查询到的文档
     * @return
     */
    public List<SearchDocument> getDocuments(){
        return this.documents;
    }

}
