package com.huasisoft.search.core.query.search;

import java.io.Serializable;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-16.
 * @Time 15:05
 * @Description 自定义查询结果HitContext，调用方不需要引入ES相关jar包
 * @Version 2.0.0
 */
public class HSHitContext implements Serializable{
    private static final long serialVersionUID = 6710992710162284498L;
    private long total;//查询总条数
    private Float max_score;//最大匹配的分数（业务部门可以不用管）
    private List<HSHit> hits;//返回的数据

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Float getMax_score() {
        return max_score;
    }

    public void setMax_score(Float max_score) {
        this.max_score = max_score;
    }

    public List<HSHit> getHits() {
        return hits;
    }

    public void setHits(List<HSHit> hits) {
        this.hits = hits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HSHitContext that = (HSHitContext) o;

        if (total != that.total) return false;
        if (max_score != null ? !max_score.equals(that.max_score) : that.max_score != null) return false;
        return hits != null ? hits.equals(that.hits) : that.hits == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (total ^ (total >>> 32));
        result = 31 * result + (max_score != null ? max_score.hashCode() : 0);
        result = 31 * result + (hits != null ? hits.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "HSHitContext{" +
                "total=" + total +
                ", max_score=" + max_score +
                ", hits=" + hits +
                '}';
    }

}
