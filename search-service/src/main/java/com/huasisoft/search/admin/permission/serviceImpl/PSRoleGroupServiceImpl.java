package com.huasisoft.search.admin.permission.serviceImpl;

import com.huasisoft.search.admin.permission.constant.RoleConstant;
import com.huasisoft.search.admin.permission.service.PSRoleGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-22.
 * @Time 10:51
 * @Description 坪山查询角色组操作接口实现<br/>
 * 该功能只是坪山区自己的业务规则，不通用，先用原生的SQL查询
 * @Version 2.0.0
 */
@Service("psRoleGroupService")
public class PSRoleGroupServiceImpl implements PSRoleGroupService {
    private static final Logger logger = LoggerFactory.getLogger(PSRoleGroupServiceImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isBelongToGroupName(String userGUID, String userGroupName) {
        String sql = "select a.department_guid as \"departmentGUID\" from risenet_usergroupdefine a, risenet_usergroupmembers b " +
                "where a.usergroup_guid = b.usergroup_guid and usergroup_name =? and b.employee_guid =? " +
                "order by b.tabindex desc";
        boolean isBelongToGroupName = false;
        try {
            List<Map<String, Object>>  result = jdbcTemplate.queryForList(sql,new Object[]{userGroupName,userGUID});
            if (null!=result&&result.size()>0){
                isBelongToGroupName = true;
            }
        }catch (RuntimeException e){
            logger.info("没有查询到{}记录",userGroupName);
            isBelongToGroupName = false;
        }
        return isBelongToGroupName;
    }

    @Override
    public boolean isBlowMsk(String userGUID) {
        String sql = "select re.employee_guid,re.department_guid from risenet_employee re where re.department_guid=? and re.employee_guid =?"
                + " union all select re.employee_guid,re.department_guid from risenet_userpositionmembers ru, "
                + " (select rf.rcid,rf.tabindex from risenet_userpositiondefine rf,risenet_department rd where "
                + " rf.department_guid = rd.rcid and rd.department_guid =?) a,risenet_employee re "
                + " where ru.employee_guid = re.rcid and "
                + " ru.userposition_guid = a.rcid and re.employee_guid=?";
        boolean isBlowMsk = false;
        try {
            List<Map<String, Object>>  result = jdbcTemplate.queryForList(sql,new Object[]{RoleConstant.MSK,userGUID,RoleConstant.MSK,userGUID});
            if (null!=result&&result.size()>0){
                isBlowMsk = true;
            }
        }catch (RuntimeException e){
            e.printStackTrace();
            logger.info("没有查询到{}属于秘书科的记录",userGUID);
            isBlowMsk = false;
        }
        return isBlowMsk;
    }

    @Override
    public boolean isBlowQld(String userGUID) {
        boolean isBlowQld = false;
        try {
            String deptNames = "";
            String qldDeptSql="select t.DEPARTMENT_GUID from RISENET_DEPARTMENT t where t.superior_guid=? or t.department_guid=? or t.department_guid=? order by t.tabindex";
            List<Map<String,Object>> deptList = jdbcTemplate.queryForList(qldDeptSql,new Object[]{RoleConstant.QLD,RoleConstant.ZWZY,RoleConstant.QLD});
            for(Map<String,Object> deptMap:deptList){
                String dept = (String) deptMap.get("DEPARTMENT_GUID");
                deptNames += ",'"+dept.toString()+"'";
            }
            if (deptNames.length()>0){
                deptNames = deptNames.substring(1);
            }else{
                deptNames = "''";
            }
            String sql = "select EMPLOYEE_GUID, EMPLOYEE_NAME from (select t.employee_guid,t.employee_name,t.tabindex from risenet_employee t,risenet_department rd " +
                    " where t.department_guid=rd.department_guid and rd.department_guid in("+deptNames+") and t.employee_guid=?"+
                    " union all " +
                    " select re.employee_guid,re.employee_name,a.tabindex " +
                    " from risenet_userpositionmembers ru, (select rf.rcid,rf.tabindex from risenet_userpositiondefine rf,risenet_department rd where " +
                    " rf.department_guid = rd.rcid and rd.department_guid in("+deptNames+")) a,risenet_employee re  where ru.employee_guid = re.rcid and " +
                    " ru.userposition_guid = a.rcid and re.employee_guid=?) order by tabindex ";
            List<Map<String,Object>> result = jdbcTemplate.queryForList(sql,new Object[]{userGUID,userGUID});
            if (null!=result&&result.size()>0){
                isBlowQld = true;
            }
        }catch (RuntimeException e){
            logger.info("没有查询到{}属于区领导的记录",userGUID);
            isBlowQld = false;
        }
        return isBlowQld;
    }

    @Override
    public boolean isBlowJld(String userGUID, String breauGUID) {
        String sql ="select RE.EMPLOYEE_GUID, RE.EMPLOYEE_NAME from risenet_department rd, risenet_employee re " +
                " where rd.department_guid = re.department_guid " +
                " and rd.department_guid <> ? " +
                " and re.employee_guid =? " +
                " and rd.department_guid=? " +
                " union all " +
                " select RE.EMPLOYEE_GUID, RE.EMPLOYEE_NAME " +
                " from risenet_userpositionmembers ru,(select rf.rcid, rf.tabindex " +
                " from risenet_userpositiondefine rf, risenet_department rd " +
                " where rf.department_guid = rd.rcid " +
                " and rd.department_guid =? ) a,risenet_employee re " +
                " where ru.employee_guid = re.rcid and ru.userposition_guid = a.rcid " +
                " and re.employee_guid =?";

        boolean isBlowJld = false;
        try {
            List<Map<String, Object>>  result = jdbcTemplate.queryForList(sql,new Object[]{RoleConstant.QLD,userGUID,breauGUID,breauGUID,userGUID});
            if (null!=result&&result.size()>0){
                isBlowJld = true;
            }
        }catch (RuntimeException e){
            logger.info("没有查询到{}属于秘书科的记录",userGUID);
            isBlowJld = false;
        }
        return isBlowJld;
    }
}
