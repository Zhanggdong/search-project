package com.huasisoft.search.core.model;

import com.huasisoft.search.core.strategy.highLight.HighlightFieldStrategyContext;
import com.huasisoft.search.core.strategy.index.IndexStrategyContext;
import com.huasisoft.search.core.strategy.indexType.IndexTypeStrategyContext;
import com.huasisoft.search.core.query.search.FilterParma;
import com.huasisoft.search.core.query.search.SearchQueryRule;
import com.huasisoft.search.core.query.search.Sort;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 11:43
 * @Description 查询参数处理
 * @Version 2.0.0
 */
public class QuerySearchParam {
    private static final Logger logger = LoggerFactory.getLogger(QuerySearchParam.class);
    private static final String UTF_8 = "UTF-8";
    private SearchQueryRule searchQueryRule;
    // 查询类型
    private String searchType;

    /**
     * Search相关参数获取<br/>
     * 1.索引名称：默认为 query
     * 2.索引类型：默认查询全部
     * 3.搜索关键字：前端输入
     * 4.分页信息：默认第一页每页显示10条
     * 5.过滤信息：高级查询和过滤查询需要
     * 6.高亮字段：如果没有传入，则走坪山业务规则，详情请查看
     *      @see HighlightFieldStrategyContext#getHighlightFields(String)
     * 7.平台标识：后期如果使用，判断来自pc端查询还是其他设备查询
     * @param request 请求参数
     * @throws Exception
     */
    public QuerySearchParam(HttpServletRequest request) throws Exception {
        // 获取查询参数, TODO 这段代码没有找到优化的方式，看着好烦
        String index = StringUtils.isNotBlank(request.getParameter("index")) ? request.getParameter("index") : null;
        String type = StringUtils.isNotBlank(request.getParameter("type")) ? request.getParameter("type") : null;
        String query = StringUtils.isNotBlank(request.getParameter("query")) ? request.getParameter("query") : "";
        String pageSize = StringUtils.isNotBlank(request.getParameter("pageSize")) ? request.getParameter("pageSize") : "10";
        String pageNum = StringUtils.isNotBlank(request.getParameter("pageNum")) ? request.getParameter("pageNum") : "1";
        String filterQueryParams = StringUtils.isNotBlank(request.getParameter("filterQueryParams")) ? request.getParameter("filterQueryParams") : "";
        String sorts = StringUtils.isNotBlank(request.getParameter("sorts")) ? request.getParameter("sorts") : "";
        String flag = StringUtils.isNotBlank(request.getParameter("flag")) ? request.getParameter("flag") : "1";
        String highLightFields = StringUtils.isNotBlank(request.getParameter("highLightFields")) ? request.getParameter("highLightFields") : "";
        String platformFlag = StringUtils.isNotBlank(request.getParameter("platformFlag")) ? request.getParameter("platformFlag") : "1";
        String searchType = StringUtils.isNotBlank(request.getParameter("searchType")) &&!"null".equals(request.getParameter("searchType"))? request.getParameter("searchType") : "searchAll";

        // 设置查询选项：默认为全部查询
        setSearchType(searchType);
        searchQueryRule = new SearchQueryRule();
        // 设置索引：默认查询所有公文类型索引
        searchQueryRule.setIndexs(setIndex(searchType,index));
        // 设置查询的索引类型：查询全部时不设置，后期会去掉该选项
        searchQueryRule.setType(setIndextype(searchType,type));
        searchQueryRule.setQuery(query);
        searchQueryRule.setPageNum(Integer.valueOf(pageNum));
        searchQueryRule.setPageSize(Integer.valueOf(pageSize));
        searchQueryRule.setFilters(setFilterPara(filterQueryParams));
        searchQueryRule.setSorts(setSorts(sorts));
        searchQueryRule.setFlag(Integer.valueOf(flag));
        searchQueryRule.setHighLight(true);
        searchQueryRule.setHighLightFields(setHighLightFields(highLightFields));
        searchQueryRule.setPlatformFlag(Integer.valueOf(platformFlag));
        setSearchQueryRule(searchQueryRule);

        // 验证查询参数是否合法
        if(!validate(searchQueryRule)){
            logger.error("查询参数{}验证不通过，请检查!",searchQueryRule);
            throw new RuntimeException("searchQueryRule parameters is validate!");
        }
    }

    private String[] setIndex(String searchType,String index) {
        if (StringUtils.isNoneBlank(index)){
            return index.split(",");
        }
        IndexStrategyContext context = new IndexStrategyContext();
        return context.getIndexs(searchType);
    }

    /**
     * 设置查询索引类型：
     * @param searchType 查询类型
     * @param type 查询的索引类型：
     * @return
     */
    private String[] setIndextype(String searchType, String type) {
        if (StringUtils.isNoneBlank(type)){
            return type.split(",");
        }
        IndexTypeStrategyContext context = new IndexTypeStrategyContext();
        return context.getIndexType(searchType);
    }


    public SearchQueryRule getSearchQueryRule() {
        return searchQueryRule;
    }

    public void setSearchQueryRule(SearchQueryRule searchQueryRule) {
        this.searchQueryRule = searchQueryRule;
    }

    /**
     * 验证规则：
     * 1.敏感词验证 定义异常抛出
     * 2.参数非法验证 定义异常抛出
     * 3.其他规则验证
     * @param searchQueryRule 查询规则对象
     * @return
     */
    private boolean validate(SearchQueryRule searchQueryRule) {
        return true;
    }

    /**
     * 设置高亮字段
     * @param index 索引名称
     * @return
     */
    /**
     * 设置高亮字段<br/>
     * 1.如果前端传入高亮字段，必须以逗号分隔（约定）
     * 2.如果前端没有传入高亮字段，选择默认高亮策略,详情请查看
     * @see HighlightFieldStrategyContext#getHighlightFields(String)
     * @param highLightFieldStr 高亮字段字符串
     * @return
     */
    private String[] setHighLightFields(String highLightFieldStr) {
        String searchType = getSearchType();
        // 高亮字段
        String[] highLightFields;
        if (StringUtils.isNoneBlank(highLightFieldStr)){
            highLightFields = highLightFieldStr.split(",");
        }else {
            HighlightFieldStrategyContext context = new HighlightFieldStrategyContext();
            highLightFields = context.getHighlightFields(searchType);
        }
        return highLightFields;
    }

    /**
     * 解析过滤参数：
     * 1.如果前端查询传入了过滤参数，需要解析处理
     * 2.处理坪山新区自定义的过滤规则：
     *   1）区领导角色查询规则
     *   2）局领导角色查询规则
     *   3）局办领导角色查询规则
     *   4）秘书科角色查询规则
     *   5）普通角色查询规则
     * 3）处理后台授权规则
     * @param filterPara
     * @return
     */
    public List<FilterParma> setFilterPara(String filterPara) {
        return null;
    }

    /**
     * 排序处理：
     * 1.前端业务排序处理：
     * 2.坪山新区自定义业务规则
     * 3.其他
     * @param sorts
     * @return
     */
    private List<Sort> setSorts(String sorts){
        return null;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}
