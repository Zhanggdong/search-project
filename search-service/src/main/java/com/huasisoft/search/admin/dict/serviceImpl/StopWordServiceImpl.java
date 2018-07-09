package com.huasisoft.search.admin.dict.serviceImpl;

import com.huasisoft.search.admin.dict.model.StopWord;
import com.huasisoft.search.admin.dict.service.StopWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 10:12
 * @Description 停词接口实现
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service
@Service("stopWordService")
public class StopWordServiceImpl implements StopWordService {
    private static final Logger logger = LoggerFactory.getLogger(AntonymWordServiceImpl.class);
    @Override
    public boolean save(StopWord stopWord) throws Exception {
        return false;
    }

    @Override
    public boolean removeById(String id) throws Exception {
        return false;
    }

    @Override
    public boolean remove(List<String> ids) throws Exception {
        return false;
    }

    @Override
    public List<StopWord> query(int pageNum, int pageSize) throws Exception {
        return null;
    }
}
