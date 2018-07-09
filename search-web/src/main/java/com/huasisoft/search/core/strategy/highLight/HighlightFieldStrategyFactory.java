package com.huasisoft.search.core.strategy.highLight;

import com.huasisoft.search.core.constant.SearchTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-17.
 * @Time 16:10
 * @Description 高亮策略设置工厂类
 * @Version 2.0.0
 */
public class HighlightFieldStrategyFactory {

    private static HighlightFieldStrategyFactory factory = new HighlightFieldStrategyFactory();
    private HighlightFieldStrategyFactory(){
    }

    private static Map<String,HighlightFieldStrategy> strategyMap = new HashMap<String,HighlightFieldStrategy>();

    static{
        strategyMap.put(SearchTypeEnum.SEARCH.getSearchType(), new SearchHighlightFieldStrategy());
        strategyMap.put(SearchTypeEnum.TITLE.getSearchType(), new TitleHighlightFieldStrategy());
        strategyMap.put(SearchTypeEnum.ZWTITLE.getSearchType(), new TitleHighlightFieldStrategy());
        strategyMap.put(SearchTypeEnum.FJNAME.getSearchType(), new TitleHighlightFieldStrategy());
        strategyMap.put(SearchTypeEnum.BANWENBIANHAO.getSearchType(), new BWBHHighlightFieldStrategy());
        strategyMap.put(SearchTypeEnum.LAIWENDANWEI.getSearchType(), new LWDWHighlightFieldStrategy());
        strategyMap.put(SearchTypeEnum.COMMENTCONTENT.getSearchType(), new ContentHighlightFieldStrategy());
        strategyMap.put(SearchTypeEnum.ZWCONTENT.getSearchType(), new ContentHighlightFieldStrategy());
        strategyMap.put(SearchTypeEnum.FJCONTENT.getSearchType(), new ContentHighlightFieldStrategy());
    }

    public HighlightFieldStrategy creator(String searchType){
        return strategyMap.get(searchType);
    }

    public static HighlightFieldStrategyFactory getInstance(){
        return factory;
    }
}
