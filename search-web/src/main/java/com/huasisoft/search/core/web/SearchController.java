package com.huasisoft.search.core.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.search.common.json.JsonResult;
import com.huasisoft.search.core.handler.SearchHandler;
import com.huasisoft.search.core.model.QuerySearchParam;
import com.huasisoft.search.core.query.search.HSSearchResponse;
import com.huasisoft.search.core.service.SearchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 10:45
 * @Description 统一查询接口
 * @Version 2.0.0
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Reference(timeout = 600000)
    private SearchService searchService;

    /**
     * 全文检索统一入口，这个接口暂时只提供坪山OA系统使用
     * <br>前端传入的参数，通</br>
     * @param request
     * @return
     */
    @RequestMapping(value = "/search", produces = "application/json; charset=UTF-8")
    public ModelAndView search(HttpServletRequest request){
        long start = System.currentTimeMillis();
        ModelAndView view = new ModelAndView("forward:/filecube/list.jsp");
        // 参数解析
        try {
            // TODO 内部人员授权信息：获取登录人员信息，这里只需要传入人员信息即可：
            // TODO 如果第三方授权如何处理
            QuerySearchParam param = new QuerySearchParam(request);
            // 调用查询服务获取结果
            JsonResult<HSSearchResponse> result = searchService.search(param.getSearchQueryRule());
            HSSearchResponse response = result.getData();
            SearchHandler handler = new SearchHandler(response);
            // 处理结果
            handler.handler();
            // 返回前端显示
            view.addObject("highlightingList", handler.getHighlightingMap());
            view.addObject("documents", handler.getDocuments());
            view.addObject("type", param.getSearchQueryRule().getType());
            view.addObject("query", param.getSearchQueryRule().getQuery());
            view.addObject("searchType", param.getSearchType());
            view.addObject("filterQueryParams", param.getSearchQueryRule().getFilters());
            view.addObject("total", response.getHits().getTotal());
            view.addObject("pageNum", param.getSearchQueryRule().getPageNum());
            view.addObject("pageSize", param.getSearchQueryRule().getPageSize());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 调用查询服务获取结果
        long end = System.currentTimeMillis();
        view.addObject("totalTime",(end - start) + "");
        return view;
    }
}
