package com.huasisoft.search.admin.permission.repository;

import com.huasisoft.search.admin.permission.constant.RoleConstant;
import com.huasisoft.search.admin.permission.model.Department;
import com.huasisoft.search.common.exception.CommonException;
import com.huasisoft.search.common.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-29.
 * @Time 16:44
 * @Description 坪山新区权限数据 Repository
 * @Version 2.0.0
 */
@Repository
public class PermissionDepartmentRepository {

    private static final Logger logger = LoggerFactory.getLogger(PermissionDepartmentRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    public List<Department> getMSKDepartmentGuids(String userGUID) {
        List<Department> departments = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select rd.department_guid as \"departmentGUID\",rd.department_name as \"departmentName\" from risenet_department rd ");
            sql.append("where rd.department_guid = ? ");
            sql.append("union all ");
            sql.append("select rd.department_guid as \"departmentGUID\",rd.department_name as \"departmentName\" from risenet_department_rel rd ");
            sql.append("where rd.department_guid = ? ");
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString(),new Object[]{RoleConstant.MSK,RoleConstant.MSKOLD});
            departments = getDepartments(mapList);
        }catch (Exception e){
            logger.info("秘书科查询组判断失败");
            e.printStackTrace();
        }
        return departments;
    }

    public List<Department> getQLDDepartmentGuids(String userGUID) {
        //获取区领导在普通局办的局办id
        List<String> jbguid = getGwgbid(userGUID);
        //剔除区领导这个局办id,修改为不删除区领导所在局办ID
        jbguid.remove(RoleConstant.QLD);
        if(jbguid.size()>0){
            //获取这个局办下所有的部门guid
            return getDepartmentByJB(jbguid);
        }else{
            return null;
        }
    }

    public List<Department> getJLDDepartmentGuids(String userGUID) {
        //获取当前局领导所有局办id
        List<String> jbguids = getGwgbid(userGUID);
        return getDepartmentByJB(jbguids);
    }

    public List<Department> getJLDGROUPDepartmentGuids(String userGUID) {
        return getJLDDepartmentGuids(userGUID);
    }

    public List<Department> getPTDepartmentGuids(String userGUID) {
        List<String> allDepartmentGUIDs = getALLdepartmentGUID(userGUID);
        if (allDepartmentGUIDs.size()==0)
            return null;
        String allBreauGUIDs = "";
        for(String s:allDepartmentGUIDs){
            allBreauGUIDs += " '"+s+"', ";
        }
        List<Department> departments = null;
        try {
            allBreauGUIDs = allBreauGUIDs.substring(0,allBreauGUIDs.length()-2);
            StringBuffer sql = new StringBuffer();
            sql.append(" select rd.department_guid as \"departmentGUID\",rd.department_name as \"departmentName\" from risenet_department rd");
            sql.append(" where rd.department_guid in(").append(allBreauGUIDs).append(")");
            sql.append(" union all ");
            sql.append(" select rd.department_guid as \"departmentGUID\",rd.department_name as \"departmentName\" from risenet_department_rel rd");
            sql.append(" where rd.department_guid in(").append(allBreauGUIDs).append(")");
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
            departments = getDepartments(mapList);
        }catch (Exception e){
            logger.info("局领导查询组判断失败");
            e.printStackTrace();
        }
        return departments;
    }

    public List<Department> getDepartmentByJB(List<String> breauGUIDs) {
        if (breauGUIDs.size()==0)
            return null;
        String allBreauGUIDs = "";
        for(String s:breauGUIDs){
            allBreauGUIDs += " '"+s+"', ";
        }
        List<Department> departments = null;
        try {
            allBreauGUIDs = allBreauGUIDs.substring(0,allBreauGUIDs.length()-2);
            StringBuffer sql = new StringBuffer();
            sql.append(" select rd.department_guid as \"departmentGUID\",rd.department_name as \"departmentName\" from risenet_department rd start with");
            sql.append(" rd.department_guid in (").append(allBreauGUIDs).append(")connect by prior rd.department_guid = rd.superior_guid");
            sql.append(" union all");
            sql.append(" select rd.department_guid as \"departmentGUID\",rd.department_name as \"departmentName\" from risenet_department_rel rd start with");
            sql.append(" rd.department_guid in (").append(allBreauGUIDs).append(")connect by prior rd.department_guid = rd.superior_guid");
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
            departments = getDepartments(mapList);
        }catch (Exception e){
            logger.info("局办部门查询失败");
            e.printStackTrace();
        }
        return departments;
    }

    public List<String> getGwgbid(String userGUID) {
        //获取所有部门id，包括岗位
        List<String> allDepartmentGUID = getALLdepartmentGUID(userGUID);
        if(allDepartmentGUID==null)
            return null;
        //获取部门所在的局办id
        String sall = "";
        for(String s:allDepartmentGUID){
            sall += " '"+s+"', ";
        }
        List<String> result = null;
        try {
            sall = sall.substring(0,sall.length()-2);
            StringBuffer sql = new StringBuffer();
            sql.append("select department_guid as \"departmentGUID\",department_name as \"departmentName\" from ");
            sql.append(" (select rd.superior_guid,rd.department_guid,rd.department_name,isbureau from risenet_department rd start with ");
            sql.append("rd.department_guid in(").append(sall).append(") connect by nocycle prior rd.superior_guid = rd.department_guid and rd.isbureau = 1");
            sql.append(") rq where isbureau = 1");
            sql.append(" union all select department_guid,department_name ");
            sql.append(" from (select rd.superior_guid,rd.department_name, rd.department_guid, rd.isbureau");
            sql.append(" from risenet_department_rel rd start with rd.department_guid in (");
            sql.append(sall).append(") connect by nocycle prior rd.superior_guid = rd.department_guid and rd.isbureau = 1) rq");
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());
            result = MapForListStr(mapList);
        }catch (Exception e){
            logger.info("局办ID查询失败");
            e.printStackTrace();
        }
        return result;
    }

    public List<String> getALLdepartmentGUID(String userGUID) {
        if(userGUID == null || "".equals(userGUID))
            return null;
        List<String> result = null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" select re.department_guid as \"departmentGUID\",rd.department_name as \"departmentName\" ");
            sql.append(" from risenet_employee re,risenet_department rd ");
            sql.append(" where re.department_guid = rd.department_guid and re.employee_guid = ?");
            sql.append(" union all  select distinct rd.department_guid as \"departmentGUID\",rd.department_name as \"departmentName\"");
            sql.append(" from risenet_userpositionmembers ru,risenet_employee re,risenet_userpositiondefine ruf,risenet_department rd");
            sql.append(" where ru.employee_guid = re.rcid and ru.userposition_guid = ruf.rcid  and ");
            sql.append(" ruf.department_guid = rd.rcid and  re.employee_guid =?");
            sql.append(" union all select rel.department_guid_old  as \"departmentGUID\",rdl.department_name as \"departmentName\"");
            sql.append(" from risenet_employee_rel rel,risenet_department_rel rdl");
            sql.append(" where rel.department_guid_old=rdl.department_guid and rel.employee_guid_new=?");
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString(),new Object[]{userGUID,userGUID,userGUID});
            result = MapForListStr(mapList);
        }catch (Exception e){
            logger.info("获取人员经历的所有部门失败");
            e.printStackTrace();
        }
        return result;
    }

    public List<String> MapForListStr(List<Map<String, Object>> mapList) {
        List<String> deptNames = new ArrayList<>();
        for(Map<String,Object> map:mapList){
            String dept = (String) map.get("departmentGUID");
            deptNames.add(dept);
        }
        return deptNames;
    }

    public String getBureauGuid(String employeeGUID) {
        if(employeeGUID == null || "".equals(employeeGUID))
            return null;
        String bureauGuid=null;
        try {
            StringBuffer sql = new StringBuffer();
            sql.append(" select rd.department_guid as \"departmentGUID\" from risenet_department rd,risenet_employee re");
            sql.append(" where re.department_guid=rd.department_guid");
            sql.append(" and rd.isbureau=1");
            List<Map<String,Object>> mapList = jdbcTemplate.queryForList(sql.toString());

            for(Map<String,Object> map:mapList){
                bureauGuid = (String) map.get("departmentGUID");
                break;
            }
        }catch (Exception e){
            logger.info("局办ID查询失败");
            e.printStackTrace();
        }
        return bureauGuid;
    }

    public List<Department> getDepartments(List<Map<String, Object>> mapList) {
        List<Department> departments = null;
        BeanUtils<Department> beanUtils = new BeanUtils<Department>();
        try {
            if (mapList!=null&&mapList.size()>0){
                departments = beanUtils.ListMap2JavaBean(mapList,Department.class);
            }
        } catch (CommonException e) {
            logger.info("转换失败!");
            e.printStackTrace();
        }
        return departments;
    }
}
