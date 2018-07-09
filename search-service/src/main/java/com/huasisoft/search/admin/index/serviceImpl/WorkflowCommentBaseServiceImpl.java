package com.huasisoft.search.admin.index.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huasisoft.search.admin.index.mapper.WorkflowCommentBaseMapper;
import com.huasisoft.search.admin.index.model.DeptBaseInfo;
import com.huasisoft.search.admin.index.model.WorkflowCommentBase;
import com.huasisoft.search.admin.index.service.DeptBaseInfoService;
import com.huasisoft.search.admin.index.service.WorkflowCommentBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:08
 * @Description 新OA数据库意见基本信息索引接口Service实现
 * @Version 2.0.0
 */
@Service
public class WorkflowCommentBaseServiceImpl implements WorkflowCommentBaseService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowCommentBaseServiceImpl.class);

    @Autowired
    private WorkflowCommentBaseMapper workflowCommentBaseMapper;
    @Autowired
    private DeptBaseInfoService deptBaseInfoService;

    @Override
    public int count() throws Exception {
        return workflowCommentBaseMapper.count();
    }

    @Override
    public List<WorkflowCommentBase> selectByPage(int pageNum, int pageSize) throws Exception {
        Map<String,Integer> param = new HashMap<>();
        param.put("pageNum",pageNum);
        param.put("pageSize",pageSize);
        List<WorkflowCommentBase> comments = workflowCommentBaseMapper.selectByPage(param);
        PageInfo<WorkflowCommentBase> pageInfo = new PageInfo<>(comments);
        for (WorkflowCommentBase comment:comments){
            List<DeptBaseInfo> departments = deptBaseInfoService.selectByWorkflowinstanceGUID(comment.getGuid());
            if (departments!=null){
                comment.setDepartments(departments);
            }
        }
        logger.info("意见基本信息的查询结果为:{}",comments);
        return comments;
    }

    @Override
    public List<WorkflowCommentBase> selectByPageFromTo(int from, int to) throws Exception {
        Map<String,Integer> param = new HashMap<>();
        param.put("from",from);
        param.put("to",to);
        List<WorkflowCommentBase> comments = workflowCommentBaseMapper.selectByPageFromTo(param);
        //logger.info("意见基本信息的查询结果为:{}",comments);
        return comments;
    }
}
