package com.huasisoft.search.demo.service;

import com.huasisoft.search.demo.model.Attachment;

import java.io.IOException;
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
    public void create(Attachment attachment) throws Exception;
    public void createBulk(List<Attachment> attachments) throws Exception;
    public List<Attachment> search(String field,String keyword);
}
