package com.huasisoft.search.config;

import com.huasisoft.search.config.beans.ESTransportClientFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-23.
 * @Time 10:37
 * @Description Elasticsearch配置
 * @Version 2.0.0
 */
@Configuration
@ComponentScan(basePackages = {"com.huasisoft.search"},
        excludeFilters = {@ComponentScan.Filter(type= FilterType.ANNOTATION,value = Controller.class)})
public class ElasticSearchElConfig {
    @Value("${es.cluster.name}")
    private String esClusterName;
    @Value("${es.cluster.elk-node1.host}")
    private String esClusterNode1Url;
    @Value("${es.cluster.elk-node2.host}")
    private String esClusterNode2Url;
    @Value("${es.cluster.elk-node3.host}")
    private String esClusterNode3Url;
    @Value("${es.cluster.elk-node1.port}")
    private Integer esClusterNode1Port;
    @Value("${es.cluster.elk-node2.port}")
    private Integer esClusterNode2Port;
    @Value("${es.cluster.elk-node3.port}")
    private Integer esClusterNode3Port;

    @Bean(name = "transportClient")
    public ESTransportClientFactoryBean transportClient() throws UnknownHostException{
        // 一定要注意,9300为elasticsearch的tcp端口
        // 集群名称
        ESTransportClientFactoryBean transportClientFactory=new ESTransportClientFactoryBean();
        transportClientFactory.setClusterName(this.esClusterName);
        transportClientFactory.setHosts(Arrays.asList(esClusterNode1Url,esClusterNode2Url,esClusterNode3Url));
        transportClientFactory.setPorts(Arrays.asList(esClusterNode1Port,esClusterNode2Port,esClusterNode3Port));
        return transportClientFactory;
    }

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
