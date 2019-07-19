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
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
//        Gson gson = new Gson();
        Goods goods = new Goods(null,11,new Date());
        String jsonStr = gson.toJson(goods);
        System.out.println(jsonStr); // {"name":"键盘","price":11,"produceDate":"Jul 10, 2019 12:27:24 PM"}

        Goods goods1 = gson.fromJson(jsonStr, Goods.class);

        System.out.println(goods1);// Goods(name=键盘, price=11, produceDate=Wed Jul 10 12:27:24 CST 2019)

    }
}
