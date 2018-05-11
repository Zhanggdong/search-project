package com.huasisoft.search.core.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.huasisoft.search.core.mapper.OfficeBaseMapper;
import com.huasisoft.search.core.model.db.DeptBaseInfo;
import com.huasisoft.search.core.model.db.OfficeBase;
import com.huasisoft.search.core.service.DeptBaseInfoService;
import com.huasisoft.search.core.service.OfficeBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    //@Autowired
    private DeptBaseInfoService deptBaseInfoService;

    @Override
    public int count() throws Exception {
        return officeBaseMapper.count();
    }

    @Override
    public List<OfficeBase> selectByPage(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum,pageSize);
        List<OfficeBase> officeBases = officeBaseMapper.selectByPage();
        for (OfficeBase office:officeBases){
            List<DeptBaseInfo> departments = deptBaseInfoService.selectByWorkflowinstanceGUID(office.getGUID());
            if (departments!=null){
                office.setDepartments(departments);
            }
        }
        logger.info("公文基本信息查询结果为:{}",officeBases);
        return officeBases;
    }
}
