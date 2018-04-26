package com.huasisoft.search.config;


import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import com.huasisoft.search.demo.model.Logs;
import com.huasisoft.search.demo.service.LogsService;
import org.apache.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


@Configuration
@EnableAspectJAutoProxy
@EnableAsync
@EnableScheduling
@PropertySource({"classpath:/properties/application.properties"})
@ComponentScan(basePackages="com.huasisoft.search")
@ImportResource("dubbo.xml")
public class AppConfig implements AsyncConfigurer,SchedulingConfigurer{

	private static Logger logger = Logger.getLogger(AppConfig.class.getName());
	
	@Value("${scheduleExector.pool.size}")  
	String schedulePoolSize;  
	@Value("${threadPool.min}")  
	String threadPoolMin;  
	@Value("${threadPool.max}")  
	String threadPoolMax;  
	@Value("${threadPool.queueSize}")  
	String threadPoolQueueSize;
	@Value("${threadPool.keepAlive}")  
	String threadPoolKeepAlive;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
	@Override
	@Bean(name="threadPoolTaskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Integer.valueOf(threadPoolMin));
        executor.setMaxPoolSize(Integer.valueOf(threadPoolMax));
        executor.setQueueCapacity(Integer.valueOf(threadPoolQueueSize));
        executor.setKeepAliveSeconds(Integer.valueOf(threadPoolKeepAlive));
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.setThreadNamePrefix("haeExecutor-");
        executor.initialize();
        return executor;
	}

	@Override 
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
	}
	
	@Bean(name="taskScheduler",destroyMethod="shutdown")
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(Integer.valueOf(schedulePoolSize));
    }

	public static void main(String[] args) throws IOException {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		logger.info("已启动，按按任意键退出");
		String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
		for(String beanDefinitionName : beanDefinitionNames){
			logger.info(beanDefinitionName);
		}
		LogsService logsService = applicationContext.getBean(LogsService.class);
		Logs log = new Logs();
		log.setUserId(1);
		log.setSystem("search-ps");
		log.setUrl("/api/logs/create");
		log.setContent("这两句是测试用的demo，可以测试整个框架的运行情况 ,spring + dubbo + elasticsearch 完整");
		System.out.println(logsService.create(log).toString());
		System.in.read();
		logger.info("停止退出");
	}
}
