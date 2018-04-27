package com.huasisoft.search.demo.mapper;

import org.apache.ibatis.annotations.Param;

public interface TestPersonMapper {

	public Integer changeNumber(@Param("id") String id, @Param("newNumber") String newNumber);
}
