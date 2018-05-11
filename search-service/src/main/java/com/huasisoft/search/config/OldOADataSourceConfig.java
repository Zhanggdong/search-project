package com.huasisoft.search.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

//@Configuration
public class OldOADataSourceConfig {

	@Value("${old.jdbc.driver}")
	String driverClass;  
	@Value("${old.jdbc.url}")
	String url;  
	@Value("${old.jdbc.username}")
	String userName;  
	@Value("${old.jdbc.password}")
	String passWord;

	@Value("${cp.initialSize}")  
	String initialSize;  
	@Value("${cp.maxActive}")  
	String maxActive;  
	@Value("${cp.maxIdle}")  
	String maxIdle;  
	@Value("${cp.minIdle}")  
	String minIdle;
	
	@Value("${cp.timeBetweenEvictionRunsMillis}")  
	String timeBetweenEvictionRunsMillis;  
	@Value("${cp.minEvictableIdleTimeMillis}")  
	String minEvictableIdleTimeMillis;  
	@Value("${cp.slowSqlMillis}")  
	String slowSqlMillis;
	@Value("${cp.validationQuery}")  
	String validationQuery ;
	@Value("${cp.filters}")
	String filters ;
	
	@SuppressWarnings("deprecation")
	@Bean(name = "oldDataSource",initMethod="init",destroyMethod="close")
	public DataSource dataSource() throws SQLException {  
		DruidDataSource dataSource = new DruidDataSource();  
	    dataSource.setDriverClassName(driverClass);  
	    dataSource.setUrl(url);  
	    dataSource.setUsername(userName);  
	    dataSource.setPassword(passWord);  
	    dataSource.setInitialSize(Integer.valueOf(initialSize));
	    dataSource.setMaxActive(Integer.valueOf(maxActive));
	    dataSource.setMaxIdle(Integer.valueOf(maxIdle));
	    dataSource.setMinIdle(Integer.valueOf(minIdle));
	    dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(timeBetweenEvictionRunsMillis));
	    dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(minEvictableIdleTimeMillis));
	    dataSource.setValidationQuery(validationQuery);
	    dataSource.setDefaultAutoCommit(false);
	    dataSource.setPoolPreparedStatements(true);
	    dataSource.setConnectionProperties("druid.stat.slowSqlMillis="+Integer.valueOf(slowSqlMillis));
	    dataSource.setRemoveAbandoned(true);
	    dataSource.setTestWhileIdle(true);
	    dataSource.setTestOnBorrow(false);
	    dataSource.setTestOnReturn(false);
	    dataSource.setFilters(filters);
	    return dataSource;  
	}

	 
}
