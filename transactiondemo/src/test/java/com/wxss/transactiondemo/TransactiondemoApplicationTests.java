package com.wxss.transactiondemo;

import com.wxss.transactiondemo.dao.GoodsDao;
import com.wxss.transactiondemo.pojo.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactiondemoApplicationTests {

    @Autowired
    GoodsDao goodsDao;

    @Test
    public void testFindAll() {
        List<Goods> all = goodsDao.findAll();

        for (Goods goods : all) {
            System.out.println(goods);
        }
    }

}
