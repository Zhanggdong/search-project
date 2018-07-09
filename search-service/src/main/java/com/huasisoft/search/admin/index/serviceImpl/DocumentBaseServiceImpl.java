package com.huasisoft.search.admin.index.serviceImpl;

import com.huasisoft.search.admin.index.mapper.DocumentBaseMapper;
import com.huasisoft.search.admin.index.model.DocumentBase;
import com.huasisoft.search.admin.index.service.DeptBaseInfoService;
import com.huasisoft.search.admin.index.service.DocumentBaseService;
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
 * @Time 15:12
 * @Description 新OA数据库正文索引接口Service实现
 * @Version 2.0.0
 */
@Service
public class DocumentBaseServiceImpl implements DocumentBaseService {
    private static final Logger logger = LoggerFactory.getLogger(DocumentBaseServiceImpl.class);

    @Autowired
    private DocumentBaseMapper documentBaseMapper;
    @Autowired
    private DeptBaseInfoService deptBaseInfoService;

    @Override
    public int count() throws Exception {
        int count = documentBaseMapper.count();
        logger.info("查询到的正文数量为:{}",count);
        return count;
    }

    @Override
    public List<DocumentBase> selectByPage(int pageNum, int pageSize) throws Exception {
        Map<String,Integer> param = new HashMap<>();
        param.put("pageNum",pageNum);
        param.put("pageSize",pageSize);
        List<DocumentBase> documents = documentBaseMapper.selectByPage(param);
        logger.info("查询到的正文为:{}",documents);
        return documents;
    }


    @Override
    public List<DocumentBase> selectByPageFromTo(int from, int to) throws Exception {
        Map<String,Integer> param = new HashMap<>();
        param.put("from",from);
        param.put("to",to);
        List<DocumentBase> documents = documentBaseMapper.selectByPageFromTo(param);
        logger.info("查询到的正文为:{}",documents);
        return documents;
    }

}
