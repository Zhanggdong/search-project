package com.huasisoft.search.demo.model;

import java.io.Serializable;

public class TestPerson implements Serializable {

/** --------------------------------------------------- serialVersionUID 序列化ID ------------------------------------------------------- */
	
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 6632003610582342733L;

/** ----------------------------------------------------- properties 属性字段 ------------------------------------------------------------- */
	
	//注释格式
	//类型     长度（取值范围）     说明      默认值         健值         索引  
	private String id ;
	private String name ;
	private String age ;
	
/** -------------------------------------------- getters and setters 属性获取和设值方法  ------------------------------------------------- */
	
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

	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
/** -------------------------------------------- constructors 构造方法  ------------------------------------------------- */
	
	/* --- default constructor 默认构造方法 ---- */
	public TestPerson() {
		super();
	}
	
	/* --- full fields constructor 全值构造方法---- */
	public TestPerson(String id, String name, String age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}
	
/** -------------------------------------------- 重写hash与equal方法  ------------------------------------------------- */
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass())
			return false;
        TestPerson other = (TestPerson) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
	
	public String toString(){
		return "TestPerson:{id:"+id+",name:"+name+"}";
	}
}