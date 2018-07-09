package com.huasisoft.search.core.query.search;

import com.huasisoft.search.core.query.base.AbstractSearchResponse;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 15:04
 * @Description 自定义封装查询响应对象，调用方不需要引入ES相关jar包
 * @Version 2.0.0
 */
public class HSSearchResponse extends AbstractSearchResponse implements Serializable{
    private static final long serialVersionUID = -393614902160630441L;

    private HSHitContext hits;//查询需要展示的结果
    private Map<String,List<HSBucket>> groupByResults; //分组结果

    public HSHitContext getHits() {
        return hits;
    }

    public void setHits(HSHitContext hits) {
        this.hits = hits;
    }

    public Map<String, List<HSBucket>> getGroupByResults() {
        return groupByResults;
    }

    public void setGroupByResults(Map<String, List<HSBucket>> groupByResults) {
        this.groupByResults = groupByResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HSSearchResponse that = (HSSearchResponse) o;

        if (hits != null ? !hits.equals(that.hits) : that.hits != null) return false;
        return groupByResults != null ? groupByResults.equals(that.groupByResults) : that.groupByResults == null;
    }

    @Override
    public int hashCode() {
        int result = hits != null ? hits.hashCode() : 0;
        result = 31 * result + (groupByResults != null ? groupByResults.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HSSearchResponse{" +
                "hits=" + hits +
                ", groupByResults=" + groupByResults +
                '}';
    }
}
