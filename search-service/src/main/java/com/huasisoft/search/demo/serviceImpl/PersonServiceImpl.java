package com.huasisoft.search.demo.serviceImpl;

import com.huasisoft.search.demo.mapper.TestPersonMapper;
import com.huasisoft.search.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@com.alibaba.dubbo.config.annotation.Service
@Service("personService")
public class PersonServiceImpl implements PersonService {

	@Autowired
	private TestPersonMapper testPersonMapper ;
	
	@Override
	@Transactional(readOnly=false)
	public void testTransaction() {
		testPersonMapper.changeNumber("1", "200");
		testPersonMapper.changeNumber("2", "400");
	}

}
