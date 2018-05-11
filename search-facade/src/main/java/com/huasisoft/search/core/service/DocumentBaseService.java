package com.huasisoft.search.core.service;

import com.huasisoft.search.core.model.db.DocumentBase;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 14:51
 * @Description 新OA数据库正文索引接口Service
 * @Version 2.0.0
 */
public interface DocumentBaseService {
    /**
     * 获取数据库正文索引数量
     * @return
     * @throws Exception
     */
    public int count() throws Exception;

    /**
     * 分页查询数据库正文索引基本信息
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<DocumentBase> selectByPage(int pageNum,int pageSize) throws Exception;
}
