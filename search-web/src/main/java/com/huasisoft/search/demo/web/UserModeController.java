package com.huasisoft.search.demo.web;

import com.huasisoft.search.common.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-29.
 * @Time 17:04
 * @Description 测试
 * @Version 2.0.0
 */
@RestController
@RequestMapping("userModel")
public class UserModeController {
    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    public JsonResult<UserMode> getUser(){
        JsonResult<UserMode> jsonResult = null;
        try {
            jsonResult = new JsonResult<UserMode>(new UserMode("12312-1","mic",new Date()));
        }catch (Exception e){
            jsonResult = new JsonResult<UserMode>(null);
        }
        return jsonResult;
    }
}
