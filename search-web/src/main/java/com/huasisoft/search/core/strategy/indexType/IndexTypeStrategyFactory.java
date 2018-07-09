package com.huasisoft.search.core.strategy.indexType;

import com.huasisoft.search.core.constant.SearchTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 18:16
 * @Description 索引类型工厂类
 * @Version 2.0.0
 */
public class IndexTypeStrategyFactory {
    private static IndexTypeStrategyFactory factory = new IndexTypeStrategyFactory();

    private IndexTypeStrategyFactory(){
    }

    private static Map<String,IndexTypeStrategy> strategyMap = new HashMap<String,IndexTypeStrategy>();

    static{
        strategyMap.put(SearchTypeEnum.SEARCH.getSearchType(), new SearchIndexTypeStrategy());
        strategyMap.put(SearchTypeEnum.TITLE.getSearchType(), new OfficeIndexTypeStrategy());
        strategyMap.put(SearchTypeEnum.ZWTITLE.getSearchType(), new DocumentIndexTypeStrategy());
        strategyMap.put(SearchTypeEnum.FJNAME.getSearchType(), new AttachmentIndexTypeStrategy());
        strategyMap.put(SearchTypeEnum.BANWENBIANHAO.getSearchType(), new OfficeIndexTypeStrategy());
        strategyMap.put(SearchTypeEnum.LAIWENDANWEI.getSearchType(), new OfficeIndexTypeStrategy());
        strategyMap.put(SearchTypeEnum.COMMENTCONTENT.getSearchType(), new CommentIndexTypeStrategy());
        strategyMap.put(SearchTypeEnum.ZWCONTENT.getSearchType(), new DocumentIndexTypeStrategy());
        strategyMap.put(SearchTypeEnum.FJCONTENT.getSearchType(), new AttachmentIndexTypeStrategy());
    }

    public IndexTypeStrategy creator(String searchType){
        return strategyMap.get(searchType);
    }

    public static IndexTypeStrategyFactory getInstance(){
        return factory;
    }
}
