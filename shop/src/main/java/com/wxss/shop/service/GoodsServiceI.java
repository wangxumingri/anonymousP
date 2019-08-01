package com.wxss.shop.service;

import com.wxss.shop.pojo.Goods;

import java.math.BigDecimal;
import java.util.List;

public interface GoodsServiceI {
    /**
     * 查询所有
     *
     * @return
     */
    List<Goods> findAll();

    /**
     * 根据商品名查询
     *
     * @param goodsName 商品名
     * @return
     */
    Goods findOneByName(String goodsName);

    // 事务测试
    void updateCount();
}
