package com.huasisoft.search.demo.serviceImpl;

import com.huasisoft.search.demo.mapper.TestPersonMapper;
import com.huasisoft.search.demo.service.TestPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;



//@Service
//@Transactional(readOnly=true)
public class TestPersonServiceImpl implements TestPersonService {

	//@Autowired
	private TestPersonMapper testPersonMapper ;
	
	@Override
	@Transactional(readOnly=false)
	public void testTransaction() {
		testPersonMapper.changeNumber("1", "200");
		testPersonMapper.changeNumber("2", "400");
	}

}
