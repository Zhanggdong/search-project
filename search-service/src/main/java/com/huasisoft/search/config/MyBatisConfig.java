package com.huasisoft.search.config;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@Configuration
@EnableTransactionManagement(proxyTargetClass=false,mode=AdviceMode.PROXY,order=201)
@MapperScan(basePackages = {"com.huasisoft.search.**.mapper"},sqlSessionFactoryRef="sqlSessionFactory",sqlSessionTemplateRef="sqlSessionTemplate")
public class MyBatisConfig {

	@Resource(name = "dataSource")
	public DataSource dataSource;
	
	@Bean(name="pageHelper")  
	public PageHelper pageHelper() {  
		Properties properties = new Properties();
		properties.put("dialect", "oracle");
		properties.put("reasonable",true);
		properties.put("returnPageInfo","always");
		properties.put("supportMethodsArguments",true);
		properties.put("params","count=countSql");
		properties.put("autoRuntimeDialect",true);
		PageHelper pageHelper = new PageHelper();  
		pageHelper.setProperties(properties); 
	    return pageHelper;  
	}
	
	@Bean(name="sqlSessionFactory")  
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {  
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();  
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setConfigLocation(new ClassPathResource("mapper/mybatis-config.xml"));
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*Mapper.xml"));
		sqlSessionFactory.setTypeAliasesPackage("com.huasisoft.search.**.model");
		sqlSessionFactory.setPlugins(new Interceptor[]{pageHelper()});
	    return sqlSessionFactory;  
	}
	
	@Bean(name="sqlSessionTemplate",destroyMethod="close")  
	@Scope("prototype")
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {  
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean().getObject());  
	    return sqlSessionTemplate;  
	}
	
	/*@Bean(name="mapperScannerConfigurer")  
	public MapperScannerConfigurer mapperScannerConfigurer() {  
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();  
		mapperScannerConfigurer.setAddToConfig(true);
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.huasisoft.hae");
	    return mapperScannerConfigurer;  
	}*/
	
	@Bean(name = "transactionManager")  
	public DataSourceTransactionManager dataSourceTransactionManager() {  
		return new DataSourceTransactionManager(dataSource);  
	}

	 
}
