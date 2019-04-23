package com.wxss.rabbitmqconsumer.mq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.wxss.rabbitmqconsumer.config.RabbitmqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Author:Created by wx on 2019/4/17
 * Desc:
 */
@Component
public class MessageHandler {

    /**
     * 自定义+手转：com.alibaba.fastjson.JSONException: syntax error,
     * @param mes
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitmqConfig.QUEUE_EMAIL)
//    public void EmailListener(String mes, Message message, Channel channel){
    public void EmailListener(String mes, Message message, Channel channel) throws UnsupportedEncodingException {
        System.out.println(message.toString());

        System.out.println(mes);

        Map map = JSONObject.parseObject(mes, Map.class);
        System.out.println(map.get("name"));

//        System.out.println("email消费");
//        System.out.println(mes); // 34,123,92,34,110,97,109,101,92,34,58,92,34,104,101,108,108,111,92,34,44,92,34,105,100,92,34,58,50,125,34
//        String str = new String(message.getBody(), "utf-8");
//        // Caused by: com.alibaba.fastjson.JSONException: syntax error, expect {, actual string, pos 31, json : "{\"name\":\"hello\",\"id\":2}"
//        System.out.println(str);// "{\"name\":\"hello\",\"id\":2}"
//        String replace = str.replace("\\", "");
//        String substring = replace.substring(1, replace.length() - 1);
//        System.out.println(substring);
//        System.out.println(replace);
//        Map map = JSONObject.parseObject(substring, Map.class);
//        System.out.println("receive:"+map.get("id")+";"+map.get("name"));
//
//        System.out.println(message.toString());
    }

    /**
     * 自定义+不转,直接发map：com.alibaba.fastjson.JSONException: syntax error,
     * @param mes
     * @param message
     */
    @RabbitListener(queues = RabbitmqConfig.QUEUE_SMS)
    public void SMSistener(Map map,Message message) throws UnsupportedEncodingException { //
        System.out.println(map.toString());
        System.out.println(map.get("name"));
        System.out.println("SMS消费");
        System.out.println(message.toString());
    }



}
