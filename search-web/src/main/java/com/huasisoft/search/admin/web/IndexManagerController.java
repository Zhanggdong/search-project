package com.huasisoft.search.admin.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.huasisoft.search.admin.model.IndexManagerBeans;
import com.huasisoft.search.admin.service.proxy.IndexManagerProxyService;
import com.huasisoft.search.common.util.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-10.
 * @Time 14:17
 * @Description TODO
 * @Version 2.0.0
 */
@RestController
@RequestMapping("core/indexs")
public class IndexManagerController {
    @Reference
    private IndexManagerProxyService indexManagerProxyService;

    @RequestMapping(value = "/createIndex" ,method = RequestMethod.PUT)
    public JsonResult<Object> createIndex(@RequestBody IndexManagerBeans beans){
        JsonResult<Object> jsonResult = null;
       try {
           boolean success = indexManagerProxyService.createIndex(beans);
           jsonResult = new JsonResult<Object>(success);
       }catch (Exception e){
           e.printStackTrace();
       }
       return jsonResult;
    }

    @RequestMapping(value = "/initIndexData" ,method = RequestMethod.PUT)
    public JsonResult<Object> initIndexData(@RequestBody IndexManagerBeans beans){
        JsonResult<Object> jsonResult = null;
        try {
            boolean success = indexManagerProxyService.initIndexData(beans);
            //Future<Boolean> pFuture = RpcContext.getContext().getFuture();
            jsonResult = new JsonResult<Object>(success);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonResult;
    }

}
