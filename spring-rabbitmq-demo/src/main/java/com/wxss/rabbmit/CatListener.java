//package com.wxss.rabbmit;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageListener;
//
//import java.io.IOException;
//
///**
// * Author:Created by wx on 2019/4/16
// * Desc:Cat监听类（消费者）
// */
//public class CatListener implements MessageListener {
//    ObjectMapper mapper = new ObjectMapper();
//
//    @Override
//    public void onMessage(Message message) {
//
//        String s = message.toString();
//        System.out.println(s);
//
////        byte[] body = message.getBody();
////        try {
////            String value = mapper.readValue(body, String.class);
////            System.out.println(value);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }
//}
