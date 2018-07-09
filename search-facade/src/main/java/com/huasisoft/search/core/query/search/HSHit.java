package com.huasisoft.search.core.query.search;

import java.io.Serializable;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 15:05
 * @Description 自定义Elasticsearch返回的Hit对象，调用方不需要引入ES相关jar包
 * @Version 2.0.0
 */
public class HSHit implements Serializable{
    private static final long serialVersionUID = -3868989410875909902L;
    private String _index;//索引（数据库类似）
    private String _type;//索引类别（数据库表类似）
    private String _id;//文档索引id
    private Float _score;//匹配的分数
    private Object _source;//真正需要展示的数据(字段与接口1 和2 插入时一样)
    private Map<String, Object> highlight;//高亮信息
    public String get_index() {
        return _index;
    }

    public void set_index(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Float get_score() {
        return _score;
    }

    public void set_score(Float _score) {
        this._score = _score;
    }

    public Object get_source() {
        return _source;
    }

    public void set_source(Object _source) {
        this._source = _source;
    }

    public Map<String, Object> getHighlight() {
        return highlight;
    }

    public void setHighlight(Map<String, Object> highlight) {
        this.highlight = highlight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HSHit hit = (HSHit) o;

        if (_index != null ? !_index.equals(hit._index) : hit._index != null) return false;
        if (_type != null ? !_type.equals(hit._type) : hit._type != null) return false;
        if (_id != null ? !_id.equals(hit._id) : hit._id != null) return false;
        if (_score != null ? !_score.equals(hit._score) : hit._score != null) return false;
        if (_source != null ? !_source.equals(hit._source) : hit._source != null) return false;
        return highlight != null ? highlight.equals(hit.highlight) : hit.highlight == null;
    }

    @Override
    public int hashCode() {
        int result = _index != null ? _index.hashCode() : 0;
        result = 31 * result + (_type != null ? _type.hashCode() : 0);
        result = 31 * result + (_id != null ? _id.hashCode() : 0);
        result = 31 * result + (_score != null ? _score.hashCode() : 0);
        result = 31 * result + (_source != null ? _source.hashCode() : 0);
        result = 31 * result + (highlight != null ? highlight.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HSHit{" +
                "_index='" + _index + '\'' +
                ", _type='" + _type + '\'' +
                ", _id='" + _id + '\'' +
                ", _score=" + _score +
                ", _source=" + _source +
                ", highlight=" + highlight +
                '}';
    }
}
