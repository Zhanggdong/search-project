package com.huasisoft.search.core.model.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeptBaseInfoExample implements Serializable{
    private static final long serialVersionUID = -354429772772708878L;
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeptBaseInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andWorkflowinstanceGUIDIsNull() {
            addCriterion("WORKFLOWINSTANCEGUID is null");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDIsNotNull() {
            addCriterion("WORKFLOWINSTANCEGUID is not null");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDEqualTo(String value) {
            addCriterion("WORKFLOWINSTANCEGUID =", value, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDNotEqualTo(String value) {
            addCriterion("WORKFLOWINSTANCEGUID <>", value, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDGreaterThan(String value) {
            addCriterion("WORKFLOWINSTANCEGUID >", value, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDGreaterThanOrEqualTo(String value) {
            addCriterion("WORKFLOWINSTANCEGUID >=", value, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDLessThan(String value) {
            addCriterion("WORKFLOWINSTANCEGUID <", value, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDLessThanOrEqualTo(String value) {
            addCriterion("WORKFLOWINSTANCEGUID <=", value, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDLike(String value) {
            addCriterion("WORKFLOWINSTANCEGUID like", value, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDNotLike(String value) {
            addCriterion("WORKFLOWINSTANCEGUID not like", value, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDIn(List<String> values) {
            addCriterion("WORKFLOWINSTANCEGUID in", values, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDNotIn(List<String> values) {
            addCriterion("WORKFLOWINSTANCEGUID not in", values, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDBetween(String value1, String value2) {
            addCriterion("WORKFLOWINSTANCEGUID between", value1, value2, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andWorkflowinstanceGUIDNotBetween(String value1, String value2) {
            addCriterion("WORKFLOWINSTANCEGUID not between", value1, value2, "workflowinstanceGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDIsNull() {
            addCriterion("SENDUSERGUID is null");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDIsNotNull() {
            addCriterion("SENDUSERGUID is not null");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDEqualTo(String value) {
            addCriterion("SENDUSERGUID =", value, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDNotEqualTo(String value) {
            addCriterion("SENDUSERGUID <>", value, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDGreaterThan(String value) {
            addCriterion("SENDUSERGUID >", value, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDGreaterThanOrEqualTo(String value) {
            addCriterion("SENDUSERGUID >=", value, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDLessThan(String value) {
            addCriterion("SENDUSERGUID <", value, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDLessThanOrEqualTo(String value) {
            addCriterion("SENDUSERGUID <=", value, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDLike(String value) {
            addCriterion("SENDUSERGUID like", value, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDNotLike(String value) {
            addCriterion("SENDUSERGUID not like", value, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDIn(List<String> values) {
            addCriterion("SENDUSERGUID in", values, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDNotIn(List<String> values) {
            addCriterion("SENDUSERGUID not in", values, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDBetween(String value1, String value2) {
            addCriterion("SENDUSERGUID between", value1, value2, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSenduserGUIDNotBetween(String value1, String value2) {
            addCriterion("SENDUSERGUID not between", value1, value2, "senduserGUID");
            return (Criteria) this;
        }

        public Criteria andSendusernameIsNull() {
            addCriterion("SENDUSERNAME is null");
            return (Criteria) this;
        }

        public Criteria andSendusernameIsNotNull() {
            addCriterion("SENDUSERNAME is not null");
            return (Criteria) this;
        }

        public Criteria andSendusernameEqualTo(String value) {
            addCriterion("SENDUSERNAME =", value, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameNotEqualTo(String value) {
            addCriterion("SENDUSERNAME <>", value, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameGreaterThan(String value) {
            addCriterion("SENDUSERNAME >", value, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameGreaterThanOrEqualTo(String value) {
            addCriterion("SENDUSERNAME >=", value, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameLessThan(String value) {
            addCriterion("SENDUSERNAME <", value, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameLessThanOrEqualTo(String value) {
            addCriterion("SENDUSERNAME <=", value, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameLike(String value) {
            addCriterion("SENDUSERNAME like", value, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameNotLike(String value) {
            addCriterion("SENDUSERNAME not like", value, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameIn(List<String> values) {
            addCriterion("SENDUSERNAME in", values, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameNotIn(List<String> values) {
            addCriterion("SENDUSERNAME not in", values, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameBetween(String value1, String value2) {
            addCriterion("SENDUSERNAME between", value1, value2, "sendusername");
            return (Criteria) this;
        }

        public Criteria andSendusernameNotBetween(String value1, String value2) {
            addCriterion("SENDUSERNAME not between", value1, value2, "sendusername");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIsNull() {
            addCriterion("DEPARTMENTNAME is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIsNotNull() {
            addCriterion("DEPARTMENTNAME is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameEqualTo(String value) {
            addCriterion("DEPARTMENTNAME =", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotEqualTo(String value) {
            addCriterion("DEPARTMENTNAME <>", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameGreaterThan(String value) {
            addCriterion("DEPARTMENTNAME >", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameGreaterThanOrEqualTo(String value) {
            addCriterion("DEPARTMENTNAME >=", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLessThan(String value) {
            addCriterion("DEPARTMENTNAME <", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLessThanOrEqualTo(String value) {
            addCriterion("DEPARTMENTNAME <=", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameLike(String value) {
            addCriterion("DEPARTMENTNAME like", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotLike(String value) {
            addCriterion("DEPARTMENTNAME not like", value, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameIn(List<String> values) {
            addCriterion("DEPARTMENTNAME in", values, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotIn(List<String> values) {
            addCriterion("DEPARTMENTNAME not in", values, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameBetween(String value1, String value2) {
            addCriterion("DEPARTMENTNAME between", value1, value2, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentnameNotBetween(String value1, String value2) {
            addCriterion("DEPARTMENTNAME not between", value1, value2, "departmentname");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDIsNull() {
            addCriterion("DEPARTMENTGUID is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDIsNotNull() {
            addCriterion("DEPARTMENTGUID is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDEqualTo(String value) {
            addCriterion("DEPARTMENTGUID =", value, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDNotEqualTo(String value) {
            addCriterion("DEPARTMENTGUID <>", value, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDGreaterThan(String value) {
            addCriterion("DEPARTMENTGUID >", value, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDGreaterThanOrEqualTo(String value) {
            addCriterion("DEPARTMENTGUID >=", value, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDLessThan(String value) {
            addCriterion("DEPARTMENTGUID <", value, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDLessThanOrEqualTo(String value) {
            addCriterion("DEPARTMENTGUID <=", value, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDLike(String value) {
            addCriterion("DEPARTMENTGUID like", value, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDNotLike(String value) {
            addCriterion("DEPARTMENTGUID not like", value, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDIn(List<String> values) {
            addCriterion("DEPARTMENTGUID in", values, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDNotIn(List<String> values) {
            addCriterion("DEPARTMENTGUID not in", values, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDBetween(String value1, String value2) {
            addCriterion("DEPARTMENTGUID between", value1, value2, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDepartmentGUIDNotBetween(String value1, String value2) {
            addCriterion("DEPARTMENTGUID not between", value1, value2, "departmentGUID");
            return (Criteria) this;
        }

        public Criteria andDnIsNull() {
            addCriterion("DN is null");
            return (Criteria) this;
        }

        public Criteria andDnIsNotNull() {
            addCriterion("DN is not null");
            return (Criteria) this;
        }

        public Criteria andDnEqualTo(String value) {
            addCriterion("DN =", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnNotEqualTo(String value) {
            addCriterion("DN <>", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnGreaterThan(String value) {
            addCriterion("DN >", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnGreaterThanOrEqualTo(String value) {
            addCriterion("DN >=", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnLessThan(String value) {
            addCriterion("DN <", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnLessThanOrEqualTo(String value) {
            addCriterion("DN <=", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnLike(String value) {
            addCriterion("DN like", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnNotLike(String value) {
            addCriterion("DN not like", value, "dn");
            return (Criteria) this;
        }

        public Criteria andDnIn(List<String> values) {
            addCriterion("DN in", values, "dn");
            return (Criteria) this;
        }

        public Criteria andDnNotIn(List<String> values) {
            addCriterion("DN not in", values, "dn");
            return (Criteria) this;
        }

        public Criteria andDnBetween(String value1, String value2) {
            addCriterion("DN between", value1, value2, "dn");
            return (Criteria) this;
        }

        public Criteria andDnNotBetween(String value1, String value2) {
            addCriterion("DN not between", value1, value2, "dn");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}