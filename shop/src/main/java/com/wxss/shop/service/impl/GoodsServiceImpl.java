package com.wxss.shop.service.impl;

import com.wxss.shop.mapper.GoodsMapper;
import com.wxss.shop.pojo.Goods;
import com.wxss.shop.pojo.GoodsExample;
import com.wxss.shop.service.GoodsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsServiceI {

    //    @Resource(name = "goodsMapper")
//    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> findAll() {
        GoodsExample goodsExample = new GoodsExample();
        return goodsMapper.selectByExample(goodsExample);
    }

    @Override
    public Goods findOneByName(String goodsName) {
        return null;
    }

    @Override
    public void updateCount() {
        Goods lx = goodsMapper.selectByPrimaryKey(1);
        lx.setCount(lx.getCount() + 50);
        goodsMapper.updateByPrimaryKey(lx);
        // 异常
        int a = 1 / 0;

        Goods hp = goodsMapper.selectByPrimaryKey(2);
        hp.setCount(hp.getCount() - 50);
        goodsMapper.updateByPrimaryKey(hp);
    }
}
