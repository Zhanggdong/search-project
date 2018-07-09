package com.huasisoft.search.core.strategy.index;

import com.huasisoft.search.core.constant.SearchTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-08.
 * @Time 15:00
 * @Description TODO
 * @Version 2.0.0
 */
public class IndexStrategyFactory {
    private static IndexStrategyFactory factory = new IndexStrategyFactory();

    private IndexStrategyFactory(){
    }

    private static Map<String,IndexStrategy> strategyMap = new HashMap<String,IndexStrategy>();

    static{
        strategyMap.put(SearchTypeEnum.SEARCH.getSearchType(), new SearchIndexStrategy());
        strategyMap.put(SearchTypeEnum.TITLE.getSearchType(), new OfficeIndexStrategy());
        strategyMap.put(SearchTypeEnum.ZWTITLE.getSearchType(), new DocumentIndexStrategy());
        strategyMap.put(SearchTypeEnum.FJNAME.getSearchType(), new AttachmentIndexStrategy());
        strategyMap.put(SearchTypeEnum.BANWENBIANHAO.getSearchType(), new OfficeIndexStrategy());
        strategyMap.put(SearchTypeEnum.LAIWENDANWEI.getSearchType(), new OfficeIndexStrategy());
        strategyMap.put(SearchTypeEnum.COMMENTCONTENT.getSearchType(), new CommentIndexStrategy());
        strategyMap.put(SearchTypeEnum.ZWCONTENT.getSearchType(), new DocumentIndexStrategy());
        strategyMap.put(SearchTypeEnum.FJCONTENT.getSearchType(), new AttachmentIndexStrategy());
    }

    public IndexStrategy creator(String searchType){
        return strategyMap.get(searchType);
    }

    public static IndexStrategyFactory getInstance(){
        return factory;
    }
}
