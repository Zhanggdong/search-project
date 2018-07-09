package com.huasisoft.search.admin.index.constant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 11:02
 * @Description 索引标识
 * @Version 2.0.0
 */
public enum DBIndexType {
    NEW_OFFICE(1),
    NEW_OFFICE_DOCUMETN(2),
    NEW_OFFICE_COMMENT(3),
    NEW_ATTACHMENT(4),
    OLD_OFFICE(5),
    OLD_OFFICE_DOCUMETN(6),
    OLD_OFFICE_COMMENT(7),
    OLD_ATTACHMENT(8);

    private Integer type;

    DBIndexType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
