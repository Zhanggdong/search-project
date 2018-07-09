package com.huasisoft.search.admin.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.huasisoft.search.admin.index.beans.IndexManagerBeans;
import com.huasisoft.search.admin.index.service.proxy.IndexManagerProxyService;
import com.huasisoft.search.common.util.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * @Description 索引管理控制类
 * 这个类用来管理索引的创建、删除、定时增量、全量索引的操作入口
 * @Version 2.0.0
 */
@RestController
@RequestMapping("core/indexs")
public class IndexManagerController {
    private static final Logger logger = LoggerFactory.getLogger(IndexManagerController.class);

    @Reference(async = true,timeout = 300000,loadbalance = "roundrobin")
    private IndexManagerProxyService indexManagerProxyService;
    // 拆分任务的阈值,3w条
    private static final int THRESHOULD = 100;
    @Value("${index.init.task.count}")
    private String indexInitTaskCount;

    /**
     * 创建索引：
     * 必须传入的有：type(索引类型 1 公文 2 正文 3 意见 4 附件)
     * indexName:索引名称
     * typeName:索引类型
     * 其他参数查看
     * @see IndexManagerBeans
     * @param beans
     * @return
     */
    @RequestMapping(value = "/createIndex" ,method = RequestMethod.PUT)
    public JsonResult<Boolean> createIndex(@RequestBody IndexManagerBeans beans){
        JsonResult<Boolean> jsonResult = null;
       try {
           boolean success = indexManagerProxyService.createIndex(beans);
           jsonResult = new JsonResult<Boolean>(success);
       }catch (Exception e){
           e.printStackTrace();
       }
       return jsonResult;
    }

    /**
     * 初始化索引
     * @param beans
     * @return
     */
    @RequestMapping(value = "/initIndexData" ,method = RequestMethod.POST)
    public JsonResult<Boolean> initIndexData(@RequestBody IndexManagerBeans beans){
        JsonResult<Boolean> jsonResult = null;
        try {
            boolean success = indexManagerProxyService.initIndexData(beans);
            Future<Boolean> pFuture = RpcContext.getContext().getFuture();
            success= pFuture.get();
            jsonResult = new JsonResult<Boolean>(success);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * 初始化索引
     * @param beans
     * @return
     */
    @RequestMapping(value = "/initIndexDataBalance" ,method = RequestMethod.POST)
    public JsonResult<Boolean> initIndexDataBalance(@RequestBody IndexManagerBeans beans){
        long start = System.currentTimeMillis();
        JsonResult<Boolean> jsonResult = null;
        try {
            long startDate = System.currentTimeMillis();
            // 计算需要写入的索引数量：
            indexManagerProxyService.count(beans);
            Future<Integer> countFuture = RpcContext.getContext().getFuture();
            int total  = countFuture.get();
            // 拆分任务
            if (total<=0){
                logger.info("没有需要构建的索引数据!");
                jsonResult = new JsonResult<Boolean>(true);
                return jsonResult;
            }
            // 初始化方法
            boolean success = false;
            // 任务数过小，没有必要查分
            if (total<=THRESHOULD){
                indexManagerProxyService.initIndexData(beans);
            }else {
                // 计算每一个任务的数量：
                int perTaskCount = (total/Integer.valueOf(indexInitTaskCount));
                // 循环调用服务:dubbo默认为随机负载均衡机制，可以根据不同的机器情况去配置权重
                for (int task=1;task<=Integer.valueOf(indexInitTaskCount);task++){
                    int from = (task-1)*perTaskCount;
                    int to = task*perTaskCount;
                    indexManagerProxyService.initIndexData(from,to,beans);
                }
            }
            Future<Boolean> pFuture = RpcContext.getContext().getFuture();
            //success = pFuture.get();
            jsonResult = new JsonResult<Boolean>(success);
            long endDate = System.currentTimeMillis();
            logger.info("用时:{}毫秒",(endDate-startDate));
        }catch (Exception e){
            logger.info("初始化索引失败!错误信息:{}",e.getMessage());
            jsonResult = new JsonResult<Boolean>(false);
        }
        long end = System.currentTimeMillis();
        logger.info("索引构建时间为:{}毫秒",(end-start));
        return jsonResult;
    }

}
