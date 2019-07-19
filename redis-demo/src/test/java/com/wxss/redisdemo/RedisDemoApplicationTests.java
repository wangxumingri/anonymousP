package com.wxss.redisdemo;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemoApplicationTests {

    @Test
    public void testGson() {

    }

    @Test
    public void testFastJson() {
        String jsonString = JSON.toJSONString(new Goods("手机", 3000,new Date()));
        System.out.println(jsonString);// {"name":"手机","price":3000,"produceDate":1562730566363}
        
        Goods goods = JSON.parseObject(jsonString, Goods.class);
        System.out.println(goods); // Goods(name=手机, price=3000, produceDate=Wed Jul 10 11:49:26 CST 2019)
    }

    @Test
    public void testJackson() {
    }
}
