package com.huasisoft.search.admin.index.service;

import com.huasisoft.search.admin.index.model.WorkflowCommentBase;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:05
 * @Description 新OA数据库意见基本信息索引接口Service
 * @Version 2.0.0
 */
public interface WorkflowCommentBaseService {
    /**
     * 获取数据库意见基本信息索引数量
     * @return
     * @throws Exception
     */
    public int count() throws Exception;

    /**
     * 分页查询数据库意见基本信息
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<WorkflowCommentBase> selectByPage(int pageNum,int pageSize) throws Exception;

    /**
     * 查询ROW_ID从from 到 to 范围的意见信息
     * @param from
     * @param to
     * @return
     * @throws Exception
     */
    public List<WorkflowCommentBase> selectByPageFromTo(int from, int  to) throws Exception;
}
