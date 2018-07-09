package com.huasisoft.search.demo.model;


import java.io.Serializable;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-05-15.
 * @Time 17:15
 * @Description MongoDB demo  User对象
 * @Version 2.0.0
 */
//@Document(collection = "test_user")
public class UserMongo implements Serializable{
    private static final long serialVersionUID = 4428674295716571748L;
    private String id;
    //@Field
    private String name;
    //@Field
    private String address;

    public UserMongo() {
    }

    public UserMongo(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMongo userMongo = (UserMongo) o;

        if (id != null ? !id.equals(userMongo.id) : userMongo.id != null) return false;
        if (name != null ? !name.equals(userMongo.name) : userMongo.name != null) return false;
        return address != null ? address.equals(userMongo.address) : userMongo.address == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserMongo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
