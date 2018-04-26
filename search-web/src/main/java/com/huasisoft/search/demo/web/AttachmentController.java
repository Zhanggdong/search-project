package com.huasisoft.search.demo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.search.common.JsonResult;
import com.huasisoft.search.common.util.Base64Util;
import com.huasisoft.search.core.exception.ExcepCodeConst;
import com.huasisoft.search.demo.model.Attachment;
import com.huasisoft.search.demo.service.AttachmentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-25.
 * @Time 16:39
 * @Description 附件检索
 * @Version 2.0.0
 */
@RestController
@RequestMapping("attachments")
public class AttachmentController {
    @Reference
    private AttachmentService attachmentService;
    @RequestMapping(value = "/create",method = RequestMethod.PUT)
    public JsonResult<Object> create(@RequestBody(required=false) Attachment attachment){
        JsonResult<Object> jsonResult = null;
        try {
            attachmentService.create(attachment);
            jsonResult = new JsonResult<Object>(attachment);
        } catch (Exception e) {
            jsonResult = new JsonResult<Object>(ExcepCodeConst.EXCHANGE_SYS_EXCEP.getMessage(),ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
            e.printStackTrace();
        }
        return jsonResult;
    }
    @RequestMapping(value = "/createBulk",method = RequestMethod.POST)
    public JsonResult<Object> createBulk(@RequestBody List<Attachment> attachments){
        JsonResult<Object> jsonResult = null;
        try {
            attachmentService.createBulk(attachments);
            jsonResult = new JsonResult<Object>(attachments);
        } catch (Exception e) {
            jsonResult = new JsonResult<Object>(ExcepCodeConst.EXCHANGE_SYS_EXCEP.getMessage(),ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
            e.printStackTrace();
        }
        return jsonResult;
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    public JsonResult<Object> search(String field,String keyword){
        JsonResult<Object> jsonResult = null;
        try {
            List<Attachment> attachments = attachmentService.search(field,keyword);
            jsonResult = new JsonResult<Object>(attachments);
        } catch (Exception e) {
            jsonResult = new JsonResult<Object>(ExcepCodeConst.EXCHANGE_SYS_EXCEP.getMessage(),ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
            e.printStackTrace();
        }
        return jsonResult;
    }
}
