package com.huasisoft.search.demo.service;

import com.huasisoft.search.demo.model.AttachmentTest;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-25.
 * @Time 15:18
 * @Description 附件测试接口
 * @Version 2.0.0
 */
public interface AttachmentTestService {
    public void create(AttachmentTest attachmentTest) throws Exception;
    public void createBulk(List<AttachmentTest> attachmentTests) throws Exception;
    public List<AttachmentTest> search(String field, String keyword);
}
