package com.huasisoft.search.admin.dict.constant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 14:17
 * @Description 词典在Redis上存储的key常量
 * @Version 2.0.0
 */
public enum DictFieldName {
    EXTEND_WORD("ik_extent_words"),
    STOP_WORD("ik_stop_words"),
    SYNONYM_WORD("ik_synonym_words"),
    HOT_WORD("ik_hot_words");
    private String key;

    DictFieldName(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
