package com.huasisoft.search.core.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.huasisoft.search.core.mapper.DocumentBaseMapper;
import com.huasisoft.search.core.model.db.DocumentBase;
import com.huasisoft.search.core.service.DocumentBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:12
 * @Description 新OA数据库正文索引接口Service实现
 * @Version 2.0.0
 */
@Service
public class DocumentBaseServiceImpl implements DocumentBaseService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentBaseServiceImpl.class);

    @Autowired
    private DocumentBaseMapper documentBaseMapper;

    @Override
    public int count() throws Exception {
        int count = documentBaseMapper.count();
        logger.info("查询到的正文数量为:{}",count);
        return count;
    }

    @Override
    public List<DocumentBase> selectByPage(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        List<DocumentBase> documentBases = documentBaseMapper.selectByPage();
        logger.info("查询到的正文为:{}",documentBases);
        return documentBases;
    }
}
