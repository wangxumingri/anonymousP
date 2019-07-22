package com.wxss.redisdemo.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
    private static  ObjectMapper objectMapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        Goods goods = new Goods("笔记本", 100, new Date());

        withoutConfigure(goods);
        System.out.println("******************");
        withConfigure(goods);

    }

    public static void withoutConfigure(Goods goods){
        try {
            System.out.println("序列化前:"+goods);
            // 序列化
            String jsonStr = objectMapper.writeValueAsString(goods);
            System.out.println("序列化后:"+jsonStr);
            // 反序列化
            goods = objectMapper.readValue(jsonStr, Goods.class);
            System.out.println("反序列化:"+goods);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void withConfigure(Goods goods){
        try {
            System.out.println("序列化前:"+goods);
            // 控制在反序列化时，如果字符串中出现实体类中无法映射（不存在，或者没有setter和其他处理方法）的字段时，是否抛出异常
            // 默认为true
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 序列化
            String jsonStr = objectMapper.writeValueAsString(goods);
            // 加了一个pojo中 没有的 count字段，上述配置为true时，会抛异常
            // com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException:
            // Unrecognized field "count" (class com.wxss.redisdemo.Goods),
            // not marked as ignorable (3 known properties: "price", "name", "produceDate"])
            String str = "{\"name\":\"笔记本\",\"price\":100,\"produceDate\":\"2019-07-22  09:22:23\",\"count\":\"111\"}";
            System.out.println("序列化后:"+jsonStr);
            // 反序列化
            goods = objectMapper.readValue(str, Goods.class);
            System.out.println("反序列化:"+goods);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
