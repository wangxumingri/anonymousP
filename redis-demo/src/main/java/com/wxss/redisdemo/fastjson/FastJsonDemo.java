package com.wxss.redisdemo.fastjson;

import com.alibaba.fastjson.JSON;
import com.wxss.redisdemo.Goods;

import java.util.Date;

/**
 * Author:Created by wx on 2019/7/10
 * Desc:
 *      默认情况下，fastJson会将 Date 类型序列化为 毫秒 ，而不是可读性较好的格式、
 *      反序列化时，正常
 *
 */
public class FastJsonDemo {
    public static void main(String[] args) {
        String jsonString = JSON.toJSONString(new Goods(null, 3000,new Date()));
        System.out.println(jsonString);// {"name":"手机","price":3000,"produceDate":1562730566363}1562736838140

        Goods goods = JSON.parseObject(jsonString, Goods.class);
        System.out.println(goods); // Goods(name=手机, price=3000, produceDate=Wed Jul 10 11:49:26 CST 2019)
    }
}
