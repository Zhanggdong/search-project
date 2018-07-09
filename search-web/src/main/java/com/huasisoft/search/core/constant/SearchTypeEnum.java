package com.huasisoft.search.core.constant;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-17.
 * @Time 14:14
 * @Description 查询类型常量
 * @Version 2.0.0
 */
public enum SearchTypeEnum {
    SEARCH("searchAll"),
    TITLE("title"),
    LAIWENDANWEI("laiwendanwei"),
    BANWENBIANHAO("banwenbianhao"),
    COMMENTCONTENT("commentContent"),
    ZWTITLE("zwtitle"),
    ZWCONTENT("zwcontent"),
    FJNAME("fjname"),
    FJCONTENT("fjcontent");

    private String searchType;

    SearchTypeEnum(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchType() {
        return searchType;
    }
}
