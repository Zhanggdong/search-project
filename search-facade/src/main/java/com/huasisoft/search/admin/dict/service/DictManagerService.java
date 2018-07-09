package com.huasisoft.search.admin.dict.service;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 9:49
 * @Description 词典管理接口，用来CRUD扩展词、停词、同义词，
 * TODO 热词不属于这一类型，需要根据用户的行为抽取
 * @Version 2.0.0
 */
public interface DictManagerService{

    /**
     * 新增词典
     * @param word 词典内容
     * @param dictType 词典类型
     */
    public void addDict(String word, int dictType);

    /**
     * 删除词典
     * @param word
     * @param dictType
     */
    public void remove(String word, int dictType);

    public void deleteLogic(String word, int dictType);
}
