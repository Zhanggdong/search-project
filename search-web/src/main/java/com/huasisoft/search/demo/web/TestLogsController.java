package com.huasisoft.search.demo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.huasisoft.search.demo.model.Logs;
import com.huasisoft.search.demo.serviceImpl.LogsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-23.
 * @Time 14:38
 * @Description Logs测试类ES操作
 * @Version 2.0.0
 */
@RestController
@RequestMapping("logs")
public class TestLogsController {
    @Reference
    private LogsService logsService;

    @RequestMapping("/createIndex")
    public boolean createIndex(){
        logsService.createIndex("logs","log");
        return true;
    }

    @RequestMapping("/create")
    public Logs create(Logs log) throws IOException {
        return logsService.create(log);
    }

    @RequestMapping("/createBatch")
    public List<Logs> createBatch(List<Logs> logs) throws IOException {
        return logsService.createBatch(logs);
    }

    @RequestMapping("/searchAll")
    public List<Logs> searchAll(){
        return logsService.searchAll();
    }
}
