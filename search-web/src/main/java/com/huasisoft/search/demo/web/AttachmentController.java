package com.huasisoft.search.demo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.search.common.util.JsonResult;
import com.huasisoft.search.common.exception.ExcepCodeConst;
import com.huasisoft.search.admin.index.model.AttachmentBase;
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
@RequestMapping("core/attachments")
public class AttachmentController {
    @Reference(timeout = 60000)
    private AttachmentService attachmentService;

    @RequestMapping(value = "/create",method = RequestMethod.PUT)
    public JsonResult<Object> create(@RequestBody(required=false) AttachmentBase attachmentTest){
        JsonResult<Object> jsonResult = null;
        try {
            attachmentService.create(attachmentTest);
            jsonResult = new JsonResult<Object>(attachmentTest);
        } catch (Exception e) {
            jsonResult = new JsonResult<Object>(ExcepCodeConst.EXCHANGE_SYS_EXCEP.getMessage(), ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
            e.printStackTrace();
        }
        return jsonResult;
    }
    @RequestMapping(value = "/createBulk")
    public JsonResult<Object> createBulk(@RequestBody(required=false) List<AttachmentBase> attachmentTests){
        JsonResult<Object> jsonResult = null;
        try {
            boolean success = attachmentService.createBulk(attachmentTests);
            jsonResult = new JsonResult<Object>(success);
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
            List<AttachmentBase> attachmentTests = attachmentService.search(field,keyword);
            jsonResult = new JsonResult<Object>(attachmentTests);
        } catch (Exception e) {
            jsonResult = new JsonResult<Object>(ExcepCodeConst.EXCHANGE_SYS_EXCEP.getMessage(),ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
            e.printStackTrace();
        }
        return jsonResult;
    }
}
