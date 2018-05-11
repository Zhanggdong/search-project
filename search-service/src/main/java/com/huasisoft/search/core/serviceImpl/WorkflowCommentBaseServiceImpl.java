package com.huasisoft.search.core.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.huasisoft.search.core.mapper.WorkflowCommentBaseMapper;
import com.huasisoft.search.core.model.db.WorkflowCommentBase;
import com.huasisoft.search.core.service.WorkflowCommentBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:08
 * @Description 新OA数据库意见基本信息索引接口Service实现
 * @Version 2.0.0
 */
//@Service
public class WorkflowCommentBaseServiceImpl implements WorkflowCommentBaseService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowCommentBaseServiceImpl.class);

    //@Autowired
    private WorkflowCommentBaseMapper workflowCommentBaseMapper;

    //@Override
    public int count() throws Exception {
        return workflowCommentBaseMapper.count();
    }

    //@Override
    public List<WorkflowCommentBase> selectByPage(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        List<WorkflowCommentBase> workflowCommentBases = workflowCommentBaseMapper.selectByPage();
        logger.info("意见基本信息的查询结果为:{}",workflowCommentBases);
        return workflowCommentBases;
    }
}
