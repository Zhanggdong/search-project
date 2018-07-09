package com.huasisoft.search.admin.dict.serviceImpl;

import com.huasisoft.search.admin.dict.service.PubSubService;
import com.huasisoft.search.common.util.JedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author 张贵东
 * @Company 华思软件
 * @date 2018-06-20.
 * @Time 15:26
 * @Description 消息发布接口
 * @Version 2.0.0
 */
@Service(value = "pubSubService")
public class PubSubServiceImpl  implements PubSubService {
    private static final Logger logger = LoggerFactory.getLogger(PubSubServiceImpl.class);

    @Async
    @Override
    public void publishAsync(String channel,String message){
        // 发送订阅通道
        try {
            JedisUtils.getInstance().publish(channel,message);
            logger.info("发布消息[:{}]到通道[{}]成功",message,channel);
        }catch (Exception e){
            logger.info("发布消息[:{}]到通道[{}]失败",message,channel);
        }
    }

}
