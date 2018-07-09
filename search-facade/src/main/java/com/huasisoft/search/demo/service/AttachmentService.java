package com.huasisoft.search.demo.service;

import com.huasisoft.search.admin.index.model.AttachmentBase;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-25.
 * @Time 15:18
 * @Description 附件测试接口
 * @Version 2.0.0
 */
public interface AttachmentService {
    public boolean create(AttachmentBase attachmentTest) throws Exception;
    public boolean createBulk(List<AttachmentBase> attachmentTests) throws Exception;
    public List<AttachmentBase> search(String field, String keyword);


}
