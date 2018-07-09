package com.huasisoft.search.admin.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-30.
 * @Time 14:55
 * @Description TODO
 * @Version 2.0.0
 */
@RestController
@RequestMapping("admin/index")
public class IndexController {

    @RequestMapping("")
    public ModelAndView index(HttpServletRequest request){
        ModelAndView view = new ModelAndView("forward:/admin/index.jsp");
        return view;
    }
}
