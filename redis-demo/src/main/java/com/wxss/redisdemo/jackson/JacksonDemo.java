package com.wxss.redisdemo.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxss.redisdemo.Goods;

import java.io.IOException;
import java.util.Date;

/**
 * Author:Created by wx on 2019/7/10
 * Desc:Jackson在对 时间 类型序列化时，默认毫秒，毫秒转换格式时，出现时区问题
 * 默认情况下，序列化后的时间比真实时间晚8个小时，解决方法：
 *      1.在Date类型的字段是使用    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
 *      2.spring boot项目中可配置
 *          spring:
 *              jackson:
 *                   time-zone: GMT+8
 *                   date-format: yyyy-MM-dd HH:mm:ss
 *      3.有没有统一的方式
 */
public class JacksonDemo {
    public static void main(String[] args) throws IOException {
        Goods goods = new Goods(null,59,new Date());
        System.out.println("序列化前："+goods); // 序列化前：Goods(name=书包, price=59, produceDate=Wed Jul 10 12:02:46 CST 2019)


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(goods);
        System.out.println("序列化后:"+jsonStr);// 序列化后:{"name":"书包","price":59,"produceDate":"2019-07-10  12:02:46"}


        goods = objectMapper.readValue(jsonStr, Goods.class);
        System.out.println("反序列化:"+goods);// 序列化前：Goods(name=书包, price=59, produceDate=Wed Jul 10 12:02:46 CST 2019)
    }
}
