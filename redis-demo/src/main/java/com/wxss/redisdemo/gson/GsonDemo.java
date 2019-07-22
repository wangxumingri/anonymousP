package com.wxss.redisdemo.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wxss.redisdemo.Goods;

import java.util.Date;

/**
 * Author:Created by wx on 2019/7/10
 * Desc: 无时区问题，但默认12小时制
 */
public class GsonDemo {

    public static void main(String[] args) {
        // 可以使用Builder创建实例Gson实例
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        // 也可以直接使用构造函数创建Gson实例
//        Gson gson = new Gson();
        Goods goods = new Goods(null,11,new Date());
        String jsonStr = gson.toJson(goods);
        System.out.println("序列化后:"+jsonStr); // {"name":"键盘","price":11,"produceDate":"Jul 10, 2019 12:27:24 PM"}

        goods = gson.fromJson(jsonStr, Goods.class);
        System.out.println("反序列化后:"+goods);// Goods(name=键盘, price=11, produceDate=Wed Jul 10 12:27:24 CST 2019)

    }
}
