package com.wxss.shop;

import com.wxss.shop.mapper.GoodsMapper;
import com.wxss.shop.service.GoodsServiceI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopApplicationTests {

    @Autowired
    GoodsServiceI goodsService;

    @Test
    public void contextLoads() {
        goodsService.updateCount();
    }

}
