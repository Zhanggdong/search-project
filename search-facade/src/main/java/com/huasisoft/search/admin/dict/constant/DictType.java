package com.huasisoft.search.admin.dict.constant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 10:04
 * @Description 字典类型常量定义
 * @Version 2.0.0
 */
public enum DictType {
    EXTEND_WORD(1),
    STOP_WORD(2),
    SYNONYM_WORD(3),
    HOT_WORD(4)
    ;
    private Integer dictType;

    DictType(Integer dictType) {
        this.dictType = dictType;
    }
}
