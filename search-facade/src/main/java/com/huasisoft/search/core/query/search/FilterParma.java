package com.huasisoft.search.core.query.search;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 11:20
 * @Description 参数过滤对象
 * @Version 2.0.0
 */
public class FilterParma {
    private String key;//刷选的字段key（必传）
    private Object maxValue;//type 为1range 时传送
    private Byte type;//0 表示term(字段不需要分词)，1 表示range，2 表示match（字段需要分词匹配）
    private Object value;//type 为term 是才需要传送(in 查询的话，这里弄个数组，字符串数组或者int 数组等等)
    private Byte rangeType;//当type 为1 时，1 表示大于等于与小于等于（gte,lte），2 表示大于与小于（gt,lt），3 表示大于等于与小于（gte,lt），4 表示小于等于与大于(lte,gt)
    private Object minValue;//type 为1range 时传送
    private boolean Numeric=true;//true 表示数字，false 表示日期
    private String format;//假如是日期，传日期格式如（yyyy-MM-dd ）
    private boolean isNested=false;//是否是内嵌字段过滤
    private String nestedPath;//内嵌字段过滤时path，比如categorys 嵌套在productPlan 里面，path 就为categorys
    private boolean isOrSearch=false;//是否是or 查询，比如区域查找湖南省的话，把包含全国的产品也需要查出来
    private Object orValue;//是or 查询时，或者需要匹配的值

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Byte getRangeType() {
        return rangeType;
    }

    public void setRangeType(Byte rangeType) {
        this.rangeType = rangeType;
    }

    public Object getMinValue() {
        return minValue;
    }

    public void setMinValue(Object minValue) {
        this.minValue = minValue;
    }

    public boolean isNumeric() {
        return Numeric;
    }

    public void setNumeric(boolean numeric) {
        Numeric = numeric;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isNested() {
        return isNested;
    }

    public void setNested(boolean nested) {
        isNested = nested;
    }

    public String getNestedPath() {
        return nestedPath;
    }

    public void setNestedPath(String nestedPath) {
        this.nestedPath = nestedPath;
    }

    public boolean isOrSearch() {
        return isOrSearch;
    }

    public void setOrSearch(boolean orSearch) {
        isOrSearch = orSearch;
    }

    public Object getOrValue() {
        return orValue;
    }

    public void setOrValue(Object orValue) {
        this.orValue = orValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterParma that = (FilterParma) o;

        if (Numeric != that.Numeric) return false;
        if (isNested != that.isNested) return false;
        if (isOrSearch != that.isOrSearch) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (maxValue != null ? !maxValue.equals(that.maxValue) : that.maxValue != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (rangeType != null ? !rangeType.equals(that.rangeType) : that.rangeType != null) return false;
        if (minValue != null ? !minValue.equals(that.minValue) : that.minValue != null) return false;
        if (format != null ? !format.equals(that.format) : that.format != null) return false;
        if (nestedPath != null ? !nestedPath.equals(that.nestedPath) : that.nestedPath != null) return false;
        return orValue != null ? orValue.equals(that.orValue) : that.orValue == null;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (maxValue != null ? maxValue.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (rangeType != null ? rangeType.hashCode() : 0);
        result = 31 * result + (minValue != null ? minValue.hashCode() : 0);
        result = 31 * result + (Numeric ? 1 : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (isNested ? 1 : 0);
        result = 31 * result + (nestedPath != null ? nestedPath.hashCode() : 0);
        result = 31 * result + (isOrSearch ? 1 : 0);
        result = 31 * result + (orValue != null ? orValue.hashCode() : 0);
        return result;
    }
}
