package com.huasisoft.search.admin.index.serviceImpl;

import com.huasisoft.search.admin.index.mapper.AttachmentBaseMapper;
import com.huasisoft.search.admin.index.model.AttachmentBase;
import com.huasisoft.search.admin.index.model.DeptBaseInfo;
import com.huasisoft.search.admin.index.service.AttachmentBaseService;
import com.huasisoft.search.admin.index.service.DeptBaseInfoService;
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
 * @Time 11:43
 * @Description 附件接口实现
 * @Version 2.0.0
 */
@Service
public class AttachmentBaseServiceImpl implements AttachmentBaseService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentBaseServiceImpl.class);

    @Autowired
    private AttachmentBaseMapper attachmentBaseMapper;
    @Autowired
    private DeptBaseInfoService deptBaseInfoService;

    @Override
    public int count() throws Exception {
        return attachmentBaseMapper.count();
    }

    @Override
    public List<AttachmentBase> selectByPage(int pageNum, int pageSize) throws Exception {
        Map<String,Integer> params = new HashMap<>();
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);
        List<AttachmentBase> attachmentBases = attachmentBaseMapper.selectByPage(params);
        for (AttachmentBase attachment:attachmentBases){
            List<DeptBaseInfo> departments = deptBaseInfoService.selectByWorkflowinstanceGUID(attachment.getGuid());
            if (departments!=null){
                attachment.setDepartments(departments);
            }
        }
        return attachmentBases;
    }


    @Override
    public List<AttachmentBase> selectByPageFromTo(int from, int to) throws Exception {
        Map<String,Integer> params = new HashMap<>();
        params.put("from",from);
        params.put("to",to);
        // TODO 这个方法关联分页查询结果数量不对，才会这样去查询
        List<AttachmentBase> attachments = attachmentBaseMapper.selectByPageFromTo(params);
        for (AttachmentBase attachment:attachments){
            List<DeptBaseInfo> deptBaseInfos = deptBaseInfoService.selectByWorkflowinstanceGUID(attachment.getGuid());
            if (deptBaseInfos!=null&&deptBaseInfos.size()>0){
                attachment.setDepartments(deptBaseInfos);
            }
        }
        //logger.info("附件信息的查询结果为:{}",attachmentBases);
        return attachments;
    }
}
