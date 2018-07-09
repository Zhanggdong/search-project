package com.huasisoft.search.admin.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.search.admin.permission.beans.PermissionBeans;
import com.huasisoft.search.admin.permission.model.Permission;
import com.huasisoft.search.admin.permission.service.proxy.PermissionProxyService;
import com.huasisoft.search.common.json.JsonResult;
import com.huasisoft.search.common.exception.ExcepCodeConst;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-23.
 * @Time 18:10
 * @Description 授权管理接口
 * @Version 2.0.0
 */
@RestController
@RequestMapping("core/permission")
public class PermissionController {
    @Reference(timeout = 600000)
    private PermissionProxyService permissionProxyService;

    @RequestMapping(value = "/init",method = RequestMethod.POST)
    public JsonResult<Object> init(@RequestBody(required = false) PermissionBeans beans){
        JsonResult<Object> jsonResult = null;
        try {
            boolean success = permissionProxyService.initDept(beans);
            jsonResult= new JsonResult<Object>(success);
        }catch (Exception e){
            jsonResult= new JsonResult<Object>(ExcepCodeConst.EXCHANGE_SYS_EXCEP.getMessage(),ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
            e.printStackTrace();
        }
        return jsonResult;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public JsonResult<Object> getPermission(String employeeGUID,Integer pageNumber,Integer pageSize){
        JsonResult<Object> jsonResult = null;
        try {
            List<Permission> permissions = new ArrayList<>(20);
            Permission permission1 = new Permission("{0A000076-0000-0000-026A-5E34000003EB}","投资科",4);
            Permission permission2 = new Permission("{0284698F-8428-48EB-862E-EA864BCD2767}","物业管理部",4);
            Permission permission3 = new Permission("{0A000076-0000-0000-026A-7EE90000048F}","办公室",4);
            Permission permission4 = new Permission("{0A000076-0000-0000-026A-6CAD00000531}","文化科",4);
            Permission permission5 = new Permission("{BFA80606-0000-0000-702C-947400000147}","安监办",4);
            Permission permission6 = new Permission("{BFA80606-0000-0000-7D99-B3C500000067}","劳动诉求服务中心",4);
            Permission permission7 = new Permission("{AC100665-0000-0000-527C-96F20000000C}","监督执法部",4);
            Permission permission8 = new Permission("{0A000076-0000-0000-0269-C8D600000707}}","人员调离组",4);
            Permission permission9 = new Permission("{AC100665-0000-0000-527C-96F20000000C}","监督执法部",4);
            Permission permission10 = new Permission("{0284698F-8428-48EB-862E-EA864BCD2767}","物业管理部",4);
            Permission permission11 = new Permission("{0A000076-0000-0000-026A-5E34000003EB}","投资科",4);
            Permission permission12 = new Permission("{0284698F-8428-48EB-862E-EA864BCD2767}","物业管理部",4);
            Permission permission13 = new Permission("{0A000076-0000-0000-026A-5E34000003EB}","投资科",4);
            Permission permission14 = new Permission("{0284698F-8428-48EB-862E-EA864BCD2767}","物业管理部",4);
            Permission permission15 = new Permission("{0A000076-0000-0000-026A-5E34000003EB}","投资科",4);
            Permission permission16 = new Permission("{0284698F-8428-48EB-862E-EA864BCD2767}","物业管理部",4);
            Permission permission17 = new Permission("{0A000076-0000-0000-026A-5E34000003EB}","投资科",4);
            Permission permission18 = new Permission("{0284698F-8428-48EB-862E-EA864BCD2767}","物业管理部",4);
            Permission permission19 = new Permission("{0A000076-0000-0000-026A-5E34000003EB}","投资科",4);
            Permission permission20 = new Permission("{0284698F-8428-48EB-862E-EA864BCD2767}","物业管理部",4);
            permissions.add(permission1);
            permissions.add(permission2);
            permissions.add(permission3);
            permissions.add(permission4);
            permissions.add(permission5);
            permissions.add(permission6);
            permissions.add(permission7);
            permissions.add(permission8);
            permissions.add(permission9);
            permissions.add(permission10);
            permissions.add(permission11);
            permissions.add(permission12);
            permissions.add(permission13);
            permissions.add(permission14);
            permissions.add(permission15);
            permissions.add(permission16);
            permissions.add(permission17);
            permissions.add(permission18);
            permissions.add(permission19);
            permissions.add(permission20);
            Map<String,Object> result = new HashMap<>();
            int end = pageNumber*pageSize;
            int start = (pageNumber-1)*pageSize+1;
            List<Permission> data = permissions.subList(start-1,end-1);
            result.put("total",permissions.size());
            result.put("pageNumber",pageNumber);
            result.put("pageSize",pageSize);
            result.put("data",data);
            jsonResult= new JsonResult<Object>(result);
        }catch (Exception e){
            jsonResult= new JsonResult<Object>(ExcepCodeConst.EXCHANGE_SYS_EXCEP.getMessage(),ExcepCodeConst.EXCHANGE_SYS_EXCEP.getState());
            e.printStackTrace();
        }
        return jsonResult;
    }

}
