package com.huasisoft.search.core.service;

import com.huasisoft.search.common.json.JsonResult;
import com.huasisoft.search.core.query.base.BaseQueryRule;
import com.huasisoft.search.core.query.search.HSSearchResponse;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 14:26
 * @Description 全文检索接口
 * @Version 2.0.0
 */
public interface SearchService{
    public JsonResult<HSSearchResponse> search(BaseQueryRule queryRule) throws Exception;
}
