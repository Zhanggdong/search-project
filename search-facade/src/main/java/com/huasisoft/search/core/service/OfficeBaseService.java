package com.huasisoft.search.core.service;

import com.huasisoft.search.core.model.db.OfficeBase;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:02
 * @Description 新OA数据库公文基本信息索引接口Service
 * @Version 2.0.0
 */
public interface OfficeBaseService {
    /**
     * 获取数据库公文基本信息索引数量
     * @return
     * @throws Exception
     */
    public int count() throws Exception;

    /**
     * 分页查询数据库公文基本信息索引
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<OfficeBase> selectByPage(int pageNum,int pageSize) throws Exception;
}
