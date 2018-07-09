package com.huasisoft.search.config.beans;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-24.
 * @Time 12:51
 * @Description TransportClient工厂
 * @Version 2.0.0
 */
public class ESTransportClientFactoryBean implements FactoryBean<TransportClient>,InitializingBean,DisposableBean {
    private String clusterName;
    private List<String> hosts;
    private List<Integer> ports;
    private static final String DEFAULT_HOST="localhost";
    private static final Integer DEFAULT_PORT=9300;

    private TransportClient client;

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public void setPorts(List<Integer> ports) {
        this.ports = ports;
    }

    public TransportClient getObject() throws Exception {
        return client;
    }

    public Class<?> getObjectType() {
        return TransportClient.class;
    }

    public boolean isSingleton() {
        return false;
    }

    public void destroy() throws Exception {
        if(client!=null)
            client.close();
    }

    public void afterPropertiesSet() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", this.clusterName)
                .put("client.transport.sniff",true)
                .put("client.transport.ping_timeout","300s")
                .build();
        client=new PreBuiltTransportClient(settings);
        if (this.hosts.size()>0){
            for (int i=0;i<hosts.size();i++){
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(this.hosts.get(i)),this.ports.get(i)));
            }
        }else {
            // 如果没有传入则使用默认的配置
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(DEFAULT_HOST),DEFAULT_PORT));
        }
    }
}
