package com.huasisoft.search.admin.permission.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.huasisoft.search.admin.permission.constant.PermissionCode;
import com.huasisoft.search.admin.permission.constant.PositionGroup;
import com.huasisoft.search.admin.permission.constant.RoleConstant;
import com.huasisoft.search.admin.permission.beans.PermissionBeans;
import com.huasisoft.search.admin.permission.model.Department;
import com.huasisoft.search.admin.permission.model.Employee;
import com.huasisoft.search.admin.permission.model.Permission;
import com.huasisoft.search.admin.permission.repository.PermissionDepartmentRepository;
import com.huasisoft.search.admin.permission.service.EmployeeService;
import com.huasisoft.search.admin.permission.service.PSDeptPermissionService;
import com.huasisoft.search.admin.permission.service.PSRoleGroupService;
import com.huasisoft.search.common.taskbuilder.TaskBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 16:56
 * @Description 坪山新区部门授权操作实现类
 * @Version 2.0.0
 */
@Service("psDeptPermissionService")
public class PSDeptPermissionServiceImpl extends AbstractPermissionServiceImpl implements PSDeptPermissionService {
    private static final Logger logger = LoggerFactory.getLogger(PSDeptPermissionServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PSRoleGroupService roleGroupService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PermissionDepartmentRepository departmentRepository;
    @Autowired
    @Qualifier("threadPoolTaskExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor;



    @Override
    public List<Department> getDepartmentGuids(String userGUID, PositionGroup positionName) {
        List<Department> departments = new ArrayList<>();
        switch(positionName){
            case MSK:
                departments = departmentRepository.getMSKDepartmentGuids(userGUID);
                break;
            case QLD:
                departments = departmentRepository.getQLDDepartmentGuids(userGUID);
                break;
            case JLD:
                departments = departmentRepository.getJLDDepartmentGuids(userGUID);
                break;
            case JLDGROUP:
                departments = departmentRepository.getJLDGROUPDepartmentGuids(userGUID);
                break;
            case PT:
                departments = departmentRepository.getPTDepartmentGuids(userGUID);
                break;
        }
        return departments;
    }

    @Override
    public boolean add(PermissionBeans beans) {
        return false;
    }

    @Override
    public boolean update(PermissionBeans beans) {
        return false;
    }

    @Override
    public boolean remove(PermissionBeans beans) {
        return false;
    }


    /**
     * 坪山新区初始化授权操作流程：
     * 1.获取人员数量
     * 2.如果人员数量大于给定的拆分阈值，则拆分任务
     * 3.如果条件2满足，将任务提交到线程池中执行
     * 4.Task的执行流程：
     *   1）分页查询人员信息
     *   2）查询每一个人员的局办信息
     *   3）角色判断：对应全区查询组，管委会为查询用特殊的标识即可，部门太多不好查询
     *   4）获取授权部门和其他授权信息，并将部门转换为授权对象信息
     *   5）批量写入每一个人员的授权信息到Redis中
     * @param beans
     * @return
     */
    private CountDownLatch latch = null;
    @Override
    public boolean init(PermissionBeans beans) {
        Long start = System.currentTimeMillis();
        boolean success = false;
        try {
            // 计算任务数量
            int total = employeeService.count();
            // 判断
            if (total>0){
                TaskBuilder taskBuilder = new TaskBuilder(total,200);
                taskBuilder.SplitTask();
                // 执行任务
                latch = new CountDownLatch(taskBuilder.getTaskCount());
                execute(taskBuilder);
                latch.await();
            }else {
                success = true;
            }
        } catch (InterruptedException e) {
            success = false;
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        logger.info("授权初始化任务处理时间为:{} 秒",(end-start)/1000);
        return success;
    }

    public void execute(TaskBuilder  builder){
        for (int pageNum=1;pageNum<=builder.getTaskCount();pageNum++){
            threadPoolTaskExecutor.execute(new Task(pageNum,builder.getPerCount()));
        }
    }

    protected class Task implements Runnable {
        private int pageNum;
        private int pageSize;

        public Task(int pageNum, int pageSize) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
        }

        @Override
        public void run() {
            logger.info("线程{}开始初始化部门授权数据",Thread.currentThread().getName());
            // 分页查询人员数据
            List<Employee> employees = employeeService.selectByPage(pageNum,pageSize);
            // 写入Redis
            boolean success = process(employees);
            latch.countDown();
        }
    }

    private boolean process(List<Employee> employees) {
        // 对于每一个人都需要进行角色判断，获取授权部门列表信息，写入Redis(可以采用批量的方式写入)
        int count = 0;
        for (Employee employee:employees){
            String bureauGuid = departmentRepository.getBureauGuid(employee.getEmployeeGUID());
            PositionGroup position = null;
            boolean isqqgroup = false;// 全区用户组查询所有件
            boolean isgwh = false;// 是否能够查询管委会文件
            boolean islbgwh = false;// 两办部门查询经手自己的管委会文件
            // 查询条件 根据部门id 是否为管委会件
            if (roleGroupService.isBelongToGroupName(employee.getEmployeeGUID(), RoleConstant.QQGROUP)) {
                isqqgroup = true;
            } else if (roleGroupService.isBlowMsk(employee.getEmployeeGUID())) {
                position = PositionGroup.MSK;
                isgwh = true;
            } else if (roleGroupService.isBlowQld(employee.getEmployeeGUID())) {
                position = PositionGroup.QLD;
                isgwh = true;
            } else if (roleGroupService.isBlowJld(employee.getEmployeeGUID(), bureauGuid)) {
                position = PositionGroup.JLD;
                if (RoleConstant.ZHBGS.equals(bureauGuid)) {
                    islbgwh = true;
                }
            } else if (roleGroupService.isBelongToGroupName(employee.getEmployeeGUID(), RoleConstant.JLDGROUP)) {
                position = PositionGroup.JLDGROUP;
            } else {
                position = PositionGroup.PT;
            }
            List<Permission> permissions = new ArrayList<>();
            // 不是全区查询组:获取授权部门
            if (!isqqgroup) {
                List<Department> departments = getDepartmentGuids(employee.getEmployeeGUID(),position);
                if (departments!=null&&departments.size()>0){
                    permissions = wrap(departments);
                }
            }
            // 特殊查询权限：是否可以查询管委会文件
            if (isgwh){
                Permission permission = new Permission(PermissionCode.GWH,RoleConstant.gwhname, Integer.valueOf(PermissionCode.GWH));
                permissions.add(permission);
            }else if (islbgwh){
                Permission permission = new Permission(PermissionCode.LBGWH,RoleConstant.JLDGROUP, Integer.valueOf(PermissionCode.LBGWH));
                permissions.add(permission);
            }else if (isqqgroup){
                // 全区查询：以特定的字段标识，不必写入全部的字段
                Permission permission = new Permission(PermissionCode.QQGROUP,RoleConstant.QQGROUP, Integer.valueOf(PermissionCode.QQGROUP));
                permissions.add(permission);
            }
            // 写入Redis
            try {
                writeRedis(permissions,employee.getEmployeeGUID());
                count++;
            }catch (Exception e){
                String json = JSONObject.toJSONString(permissions);
                logger.info("人员{}写入失败的数据条数为:{}",employee.toString(),json);
                e.printStackTrace();
            }
        }
        logger.info("写入的数据条数为:{}",count);
        return false;
    }

    private void writeRedis(final List<Permission> permissions,final String employeeGUID) throws DataAccessException {
        long start = System.currentTimeMillis();
        Object result = redisTemplate.execute(new SessionCallback() {
                @Override
                public List<Object> execute(RedisOperations redisOperations) throws DataAccessException {
                    // 开启Redis 事务
                    redisOperations.multi();
                    List<String> jsons = new ArrayList<>();
                    for (Permission permission:permissions){
                        String json = JSONObject.toJSONString(permission);
                        jsons.add(json);
                    }
                    logger.info("写入的数据为:{}",jsons);
                    redisTemplate.opsForList().leftPushAll("search:"+employeeGUID,jsons);
                    List<Object> result = redisOperations.exec();
                    return result;
                }
            });
        long end = System.currentTimeMillis();
        logger.info("result:" + result + ",millis:" + (end - start));
    }

    private List<Permission> wrap(List<Department> departments) {
        List<Permission> permissions = new ArrayList<>();
        for (Department department:departments){
            Permission permission = new Permission(department.getDepartmentGUID(),department.getDepartmentName(), Integer.valueOf(PermissionCode.DEPT));
            permissions.add(permission);
        }
        return permissions;
    }
}
