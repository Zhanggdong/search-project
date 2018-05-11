package com.huasisoft.search.config;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-04-28.
 * @Time 11:06
 * @Description mongodb 配置
 * @Version 2.0.0
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.huasisoft.search.**.repository")
@EnableMongoAuditing
public class MongoConfig extends AbstractMongoConfiguration {
    private static Logger logger = LoggerFactory.getLogger(MongoConfig.class.getName());

    @Value("${mongodb.host}")
    String mgdbHost;
    @Value("${mongodb.port}")
    Integer mgdbPort;
    @Value("${mongodb.dbName}")
    String mgdbName;
    @Value("${mongodb.auth}")
    boolean mgdbAuth;
    @Value("${mongodb.user}")
    String mgdbUser;
    @Value("${mongodb.pwd}")
    String mgdbPwd;

    @Value("${mongodb.connectionsPerHost}")
    Integer connectionsPerHost ;
    @Value("${mongodb.threadsAllowedToBlockForConnectionMultiplier}")
    Integer threadsAllowedToBlockForConnectionMultiplier ;
    @Value("${mongodb.connectTimeout}")
    Integer connectTimeout ;
    @Value("${mongodb.maxWaitTime}")
    Integer maxWaitTime ;
    @Value("${mongodb.autoConnectRetry}")
    boolean autoConnectRetry ;
    @Value("${mongodb.socketKeepAlive}")
    boolean socketKeepAlive ;
    @Value("${mongodb.socketTimeout}")
    Integer socketTimeout ;

    @Value("${mongodb.slaveOk}")
    boolean slaveOk ;
    @Value("${mongodb.writeNumber}")
    Integer writeNumber ;
    @Value("${mongodb.riteTimeout}")
    Integer riteTimeout ;
    @Value("${mongodb.writeFsync}")
    boolean writeFsync ;

    @Override
    protected String getDatabaseName() {
        return mgdbName;
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClient mongoClient;
        MongoCredential credential = MongoCredential.createMongoCRCredential(mgdbUser, mgdbName,mgdbPwd.toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(connectionsPerHost).threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnectionMultiplier)
                .socketKeepAlive(socketKeepAlive).socketTimeout(socketTimeout).heartbeatSocketTimeout(connectTimeout).heartbeatConnectTimeout(maxWaitTime).maxConnectionIdleTime(maxWaitTime)
                .autoConnectRetry(autoConnectRetry).maxConnectionLifeTime(0).readPreference(ReadPreference.secondary()).build();
        List<ServerAddress> addresses = new ArrayList<>();
        String[] str = this.mgdbHost.split(",");
        for (String strHost : str) {
            ServerAddress address = new ServerAddress(strHost, mgdbPort);
            addresses.add(address);
        }
        if (mgdbAuth) {
            mongoClient = new MongoClient(addresses, Arrays.asList(credential), options);
        } else {
            mongoClient = new MongoClient(addresses, options);
        }
        return mongoClient;

    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws Exception{
        return new SimpleMongoDbFactory(mongo(),mgdbName);
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo(), mgdbName);
        logger.info("*******" + mongoTemplate.getDb().getName());
        return mongoTemplate;
    }

}
