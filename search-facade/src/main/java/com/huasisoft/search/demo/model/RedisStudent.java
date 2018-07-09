package com.huasisoft.search.demo.model;


import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-18.
 * @Time 16:37
 * @Description Redis 使用案例
 * @Version 2.0.0
 */
@RedisHash("RedisStudent")
public class RedisStudent implements Serializable{
    private static final long serialVersionUID = 4007441150847034238L;

    public enum Gender {
        MALE, FEMALE
    }

    public RedisStudent() {
    }

    public RedisStudent(String id, String name, Gender gender, int grade) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.grade = grade;
    }

    private String id;
    private String name;
    private Gender gender;
    private int grade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RedisStudent that = (RedisStudent) o;

        if (grade != that.grade) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return gender == that.gender;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + grade;
        return result;
    }
}
