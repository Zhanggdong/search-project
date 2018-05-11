package com.huasisoft.search.core.service;

import com.huasisoft.search.core.model.db.AttachmentBase;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 11:40
 * @Description 附件操作接口Service
 * @Version 2.0.0
 */
public interface AttachmentBaseService {
    /**
     * 计算数据库中满足索引附件的数量
     * @return
     * @throws Exception
     */
    public int count() throws Exception;

    /**
     * 分页查询数据库中满足索引附件
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<AttachmentBase> selectByPage(int pageNum,int pageSize) throws Exception;
}
