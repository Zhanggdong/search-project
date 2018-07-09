package com.huasisoft.search.admin.index.beans;

import com.huasisoft.search.admin.index.constant.DBIndexType;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-07.
 * @Time 10:54
 * @Description 索引操作Beans
 * @Version 2.0.0
 */
public class IndexManagerBeans implements Serializable{
    private static final long serialVersionUID = 1817924356172833338L;
    /**
     * 使用哪个Service执行
     */
    private String beanName;
    /**
     * 执行的索引类型
     */
    private Integer type;
    /**
     *  索引数据
     */
    private Object data;
    /**
     * 默认增量执行新OA数据库的公文基本信息索引
     */
    private static final Integer DEFAULT_TYPE= DBIndexType.NEW_OFFICE.getType();
    /**
     * 是否立即执行
     */
    private boolean immediately;

    /**
     * 是否增量执行：默认是增量执行
     */
    private boolean increment;
    /**
     * 索引名称
     */
    private String indexName;
    /**
     * 默认索引名称
     */
    private static final String DEFAULT_INDEXNAME = "office";
    /**
     * 索引类型
     */
    private String typeName;
    /**
     * 索引定义
     */
    private String mapping;
    /**
     * 分片数量
     */
    private Integer shards;
    /**
     * 副本数量
     */
    private Integer replicas;


    public IndexManagerBeans() {
        this(DEFAULT_TYPE,true,true);
    }

    public IndexManagerBeans(Integer type) {
        this(type,true,true);
        this.type = type;
    }

    public IndexManagerBeans(Integer type, boolean increment) {
        this(type,true,increment);
        this.type = type;
        this.increment = increment;
    }

    public IndexManagerBeans(Integer type, boolean immediately, boolean increment) {
        this.type = type;
        this.immediately = immediately;
        this.increment = increment;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static Integer getDefaultType() {
        return DEFAULT_TYPE;
    }

    public boolean isImmediately() {
        return immediately;
    }

    public void setImmediately(boolean immediately) {
        this.immediately = immediately;
    }

    public boolean isIncrement() {
        return increment;
    }

    public void setIncrement(boolean increment) {
        this.increment = increment;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public static String getDefaultIndexname() {
        return DEFAULT_INDEXNAME;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public Integer getShards() {
        return shards;
    }

    public void setShards(Integer shards) {
        this.shards = shards;
    }

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexManagerBeans beans = (IndexManagerBeans) o;

        if (immediately != beans.immediately) return false;
        if (increment != beans.increment) return false;
        if (beanName != null ? !beanName.equals(beans.beanName) : beans.beanName != null) return false;
        if (type != null ? !type.equals(beans.type) : beans.type != null) return false;
        if (data != null ? !data.equals(beans.data) : beans.data != null) return false;
        if (indexName != null ? !indexName.equals(beans.indexName) : beans.indexName != null) return false;
        if (typeName != null ? !typeName.equals(beans.typeName) : beans.typeName != null) return false;
        if (mapping != null ? !mapping.equals(beans.mapping) : beans.mapping != null) return false;
        if (shards != null ? !shards.equals(beans.shards) : beans.shards != null) return false;
        return replicas != null ? replicas.equals(beans.replicas) : beans.replicas == null;
    }

    @Override
    public int hashCode() {
        int result = beanName != null ? beanName.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (immediately ? 1 : 0);
        result = 31 * result + (increment ? 1 : 0);
        result = 31 * result + (indexName != null ? indexName.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (mapping != null ? mapping.hashCode() : 0);
        result = 31 * result + (shards != null ? shards.hashCode() : 0);
        result = 31 * result + (replicas != null ? replicas.hashCode() : 0);
        return result;
    }

    public TaskBuilder builder(int total){
        return new TaskBuilder(total);
    }

    public class TaskBuilder{
        /**
         * 数据总量
         */
        int total;
        /**
         * 总任务数量
         */
        int taskCount;
        /**
         *  当前执行到第几个任务
         */
        int currentTask;
        /**
         * 每次执行多条数据：需要从配置中获取
         */
        int perCount = 1000;

        public TaskBuilder() {
        }

        public TaskBuilder(int total) {
            this.total = total;
        }

        public int getTotal() {
            return total;
        }

        public int getTaskCount() {
            return taskCount;
        }

        public int getCurrentTask() {
            return currentTask;
        }

        public int getPerCount() {
            return perCount;
        }

        public void setPerCount(int perCount) {
            this.perCount = perCount;
        }

        /**
         * 拆分任务
         */
        public void SplitTask(){
           taskCount = (total-1)/perCount+1;
        }
    }
}
