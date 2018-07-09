package com.huasisoft.search.admin.index.serviceImpl;

import com.huasisoft.search.admin.index.mapper.OfficeBaseMapper;
import com.huasisoft.search.admin.index.model.DeptBaseInfo;
import com.huasisoft.search.admin.index.model.OfficeBase;
import com.huasisoft.search.admin.index.service.DeptBaseInfoService;
import com.huasisoft.search.admin.index.service.OfficeBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-04.
 * @Time 15:18
 * @Description 新OA数据库公文基本信息索引接口Service实现
 * @Version 2.0.0
 */
@Service
public class OfficeBaseServiceImpl implements OfficeBaseService{
    private static final Logger logger = LoggerFactory.getLogger(OfficeBaseServiceImpl.class);

    @Autowired
    private OfficeBaseMapper officeBaseMapper;
    @Autowired
    private DeptBaseInfoService deptBaseInfoService;

    @Override
    public int count() throws Exception {
        return officeBaseMapper.count();
    }

    @Override
    public List<OfficeBase> selectByPage(int pageNum, int pageSize) throws Exception {
        Map<String,Integer> params = new HashMap<String,Integer>();
        params.put("pageNum",pageNum);
        params.put("pageSize",pageSize);
        List<OfficeBase> officeBases = officeBaseMapper.selectByPage(params);
        for (OfficeBase office:officeBases){
            List<DeptBaseInfo> departments = deptBaseInfoService.selectByWorkflowinstanceGUID(office.getGuid());
            if (departments!=null){
                office.setDepartments(departments);
            }
        }
        logger.info("公文基本信息查询结果为:{}",officeBases);
        return officeBases;
    }


    @Override
    public List<OfficeBase> selectByPageFromTo(int from, int to) throws Exception {
        long start = System.currentTimeMillis();
        Map<String,Integer> params = new HashMap<String,Integer>();
        params.put("from",from);
        params.put("to",to);
        List<OfficeBase> officeBases = officeBaseMapper.selectByPageFromTo(params);
        long end = System.currentTimeMillis();
        logger.info("公文意见基本信息查询时间为:{}毫秒",end-start);
        return officeBases;
    }


}
