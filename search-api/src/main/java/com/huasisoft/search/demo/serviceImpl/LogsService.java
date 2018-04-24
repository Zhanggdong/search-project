package com.huasisoft.search.demo.serviceImpl;

import com.huasisoft.search.demo.model.Logs;

import java.io.IOException;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-23.
 * @Time 11:46
 * @Description Logs测试类
 * @Version 2.0.0
 */
public interface LogsService {

    public void createIndex(String index,String type);

    public Logs create(Logs log) throws IOException;

    public List<Logs> createBatch(List<Logs> logs) throws IOException;

    public List<Logs> searchAll();
}
