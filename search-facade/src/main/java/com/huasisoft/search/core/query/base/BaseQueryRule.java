package com.huasisoft.search.core.query.base;

import com.huasisoft.search.core.query.search.FilterParma;
import com.huasisoft.search.core.query.search.Sort;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-14.
 * @Time 20:43
 * @Description 查询规则基类
 * @Version 2.0.0
 */
public abstract class BaseQueryRule implements Serializable{
    private static final long serialVersionUID = 4924593132322277011L;
    /**
     * 索引名称：理论上跟数据库名对应（跟es 负责人一起确认）
     */
    private String[] indexs;
    /**
     * 默认的索引
     */
    private static final String DEFAULT_INDEX = "office";
    /**
     * 索引类型：理论上跟数据库表名对应
     */
    private String[] type;
    /**
     * 搜索关键词：像百度搜索一样输入关键词
     */
    private String query;
    /**
     * 分页时每页大小
     */
    private int pageSize;
    /**
     * 分页时显示第几页
     */
    private int pageNum;
    /**
     * 过滤信息：相当于sql 里的where 后面的过滤语句（where id=1）
     */
    private List<FilterParma> filters;
    /**
     * 排序
     */
    private List<Sort> sorts;//排序
    /**
     * 搜索业务标识:1 公文、
     */
    private int flag;
    /**
     * 是否高亮
     */
    private boolean highLight;
    /**
     * 需要高亮的字段
     */
    private String[] highLightFields;
    /**
     * 平台标识：1、pc,
     */
    private int platformFlag;

    public String[] getIndexs() {

        if (indexs==null&&indexs.length==0){
            this.indexs = DEFAULT_INDEX.split(",");
        }
        return indexs;
    }

    public void setIndexs(String[] indexs) {
        this.indexs = indexs;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<FilterParma> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterParma> filters) {
        this.filters = filters;
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public void setSorts(List<Sort> sorts) {
        this.sorts = sorts;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isHighLight() {
        return highLight;
    }

    public void setHighLight(boolean highLight) {
        this.highLight = highLight;
    }

    public String[] getHighLightFields() {
        return highLightFields;
    }

    public void setHighLightFields(String[] highLightFields) {
        this.highLightFields = highLightFields;
    }

    public int getPlatformFlag() {
        return platformFlag;
    }

    public void setPlatformFlag(int platformFlag) {
        this.platformFlag = platformFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseQueryRule that = (BaseQueryRule) o;

        if (pageSize != that.pageSize) return false;
        if (pageNum != that.pageNum) return false;
        if (flag != that.flag) return false;
        if (highLight != that.highLight) return false;
        if (platformFlag != that.platformFlag) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(indexs, that.indexs)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(type, that.type)) return false;
        if (query != null ? !query.equals(that.query) : that.query != null) return false;
        if (filters != null ? !filters.equals(that.filters) : that.filters != null) return false;
        if (sorts != null ? !sorts.equals(that.sorts) : that.sorts != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(highLightFields, that.highLightFields);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(indexs);
        result = 31 * result + Arrays.hashCode(type);
        result = 31 * result + (query != null ? query.hashCode() : 0);
        result = 31 * result + pageSize;
        result = 31 * result + pageNum;
        result = 31 * result + (filters != null ? filters.hashCode() : 0);
        result = 31 * result + (sorts != null ? sorts.hashCode() : 0);
        result = 31 * result + flag;
        result = 31 * result + (highLight ? 1 : 0);
        result = 31 * result + Arrays.hashCode(highLightFields);
        result = 31 * result + platformFlag;
        return result;
    }
}
