package com.wxss.demo.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wxss.demo.entity.Goods;
import com.wxss.demo.entity.GoodsExample;
import com.wxss.demo.mapper.GoodsMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class Service {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
//    @Qualifier("myRedisTemplate") // 此时可以不用，虽然创建bean的时候，指定名称，但目前容器内只有一个RedisTemplate
    private RedisTemplate redisTemplate;

    public List<Goods> findAll() {
        // 从缓存中获取数据
        String goodStr = (String) redisTemplate.opsForValue().get("goods");// Oject
        // 定义要返回的list
        List<Goods> goodsList = null;

        if (StringUtils.isNotEmpty(goodStr)) {
            System.out.println("从缓存中获取" + goodStr);
            // 将 JSON串 转成 List
            goodsList = JSONArray.parseArray(goodStr, Goods.class);
//            goodsList = JSONObject.parseArray(goodStr, Goods.class);

        } else {
            // 缓存中没有数据
            System.out.println("从数据库中查询");
            GoodsExample goodsExample = new GoodsExample();
            goodsList = goodsMapper.selectByExample(goodsExample);

            if (CollectionUtils.isNotEmpty(goodsList)) {
                // 将 List 转成 JSON串
                String jsonString = JSONArray.toJSONString(goodsList);
                // 存入缓存中
                redisTemplate.opsForValue().set("goods", jsonString);
            }

        }
        // 返回结果
        return goodsList;
    }
}
