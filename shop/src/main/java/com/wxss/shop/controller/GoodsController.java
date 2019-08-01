package com.wxss.shop.controller;

import com.wxss.shop.pojo.Goods;
import com.wxss.shop.service.GoodsServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsServiceI goodsService;

    @GetMapping(value = "/findAll", produces = "application/json;charset=UTF-8")
    public List<Goods> findAll() {
        List<Goods> goodsList = goodsService.findAll();

        if (CollectionUtils.isNotEmpty(goodsList)) {
            for (Goods goods : goodsList) {
                System.out.println(goods.getCreateTime());
            }

            return goodsList;
        } else {
            return null;
        }
    }
}
