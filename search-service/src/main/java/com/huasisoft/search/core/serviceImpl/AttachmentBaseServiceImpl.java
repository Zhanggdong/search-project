package com.huasisoft.search.core.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.huasisoft.search.core.mapper.AttachmentBaseMapper;
import com.huasisoft.search.core.model.db.AttachmentBase;
import com.huasisoft.search.core.service.AttachmentBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public int count() throws Exception {
        return attachmentBaseMapper.count();
    }

    @Override
    public List<AttachmentBase> selectByPage(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        List<AttachmentBase> attachmentBases = attachmentBaseMapper.selectByPage();
        logger.info("附件信息的查询结果为:{}",attachmentBases);
        return attachmentBases;
    }
}
