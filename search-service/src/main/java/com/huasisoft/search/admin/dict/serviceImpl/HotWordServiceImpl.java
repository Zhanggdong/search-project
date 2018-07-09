package com.huasisoft.search.admin.dict.serviceImpl;

import com.huasisoft.search.admin.dict.model.HotWord;
import com.huasisoft.search.admin.dict.service.HotWordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 10:11
 * @Description 热词接口实现
 * @Version 2.0.0
 */
@com.alibaba.dubbo.config.annotation.Service
@Service("hotWordService")
public class HotWordServiceImpl implements HotWordService {

    private static final Logger logger = LoggerFactory.getLogger(HotWordServiceImpl.class);

    @Override
    public boolean save(HotWord hotWord) throws Exception {
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
    public List<HotWord> query(int pageNum, int pageSize) throws Exception {
        return null;
    }
}
