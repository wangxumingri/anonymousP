package com.wxss.rabbitmqproducer;

import com.alibaba.fastjson.JSONObject;
import com.wxss.rabbitmqproducer.config.RabbitmqConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:Created by wx on 2019/4/17
 * Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Producer {

    @Autowired
//    @Qualifier("myTemplate")
    private RabbitTemplate rabbitTemplate;

    /**
     * 发给EMAIL队列
     */
    @Test
    public void sendMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 2);
        map.put("name", "hello");
//        String jsonString = JSONObject.toJSONString(map);
        // 将消息发送给EMAIL队列
//        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_DIRECT,RabbitmqConfig.ROUTING_EMAIL,jsonString);
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_DIRECT, RabbitmqConfig.ROUTING_EMAIL, map);
//        System.out.println("send:"+jsonString);
    }

    /**
     * 发给SMS队列
     */
    @Test
    public void sendMapByCustomeTemplate() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 2);
        map.put("name", "hello world");
//        String jsonString = JSONObject.toJSONString(map);
        // 将消息发送给EMAIL队列
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_DIRECT, RabbitmqConfig.ROUTING_SMS, map);
//        System.out.println("send:"+jsonString);
    }


    /**
     * 事务测试
     */
    @Test
    public void testTransaction() {

    }
}
