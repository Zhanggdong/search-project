package com.huasisoft.search.config;

import com.huasisoft.search.config.beans.ESTransportClientFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-23.
 * @Time 10:37
 * @Description Elasticsearch配置
 * @Version 2.0.0
 */
@Configuration
@ComponentScan(basePackages = {"com.huasisoft.search"}, excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,value = Controller.class)})
@PropertySource({"classpath:/properties/elasticearch.properties"})
public class ElasticSearchElConfig {
    @Value("${es.cluster.name}")
    private String esClusterName;
    @Value("${es.cluster.host}")
    private String esClusterUrl;
    @Value("${es.cluster.port}")
    private String esClusterPort;

    @Bean(name = "transportClient")
    public ESTransportClientFactoryBean transportClient() throws UnknownHostException{
        // 一定要注意,9300为elasticsearch的tcp端口
        ESTransportClientFactoryBean transportClientFactory=new ESTransportClientFactoryBean();
        // 集群名称
        transportClientFactory.setClusterName(this.esClusterName);
        String[] hosts = esClusterUrl.split(",");
        String[] ports = esClusterPort.split(",");
        if (hosts==null||hosts.length<=0){
            throw new RuntimeException("Elasticsearch配置信息错误，请检查!");
        }
        // 集群设置
        Integer[] portArr = new Integer[ports.length];
        List<String> hostArr = new ArrayList<>(ports.length);
        for(int i=0;i<ports.length;i++){
            portArr[i] = Integer.valueOf(ports[i]);
            hostArr.add(hosts[i]);
        }
        transportClientFactory.setHosts(hostArr);
        transportClientFactory.setPorts(Arrays.asList(portArr));
        return transportClientFactory;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
