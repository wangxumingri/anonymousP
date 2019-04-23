package service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import pojo.Goods;
import service.GoodsServiceI;

import javax.jms.*;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsServiceI {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private JmsTemplate jmsTemplate; // jms模板类

    @Autowired
    @Qualifier("queueTextDestination")
    private Destination destination; // 目标对象

    @Override
    public void save(Goods goods) {
        goodsMapper.saveGoods(goods);
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String jsonString = JSONArray.toJSONString(goods); // 将对象转换成json串
                System.out.println(jsonString); // {"goodsName":"未来笔记本电脑","goodsdesc":"游戏电脑","price":99999.9,"title":"游戏发烧级"}
                TextMessage textMessage = session.createTextMessage(jsonString);
                System.out.println(textMessage.getText()); // {"goodsName":"未来笔记本电脑","goodsdesc":"游戏电脑","price":99999.9,"title":"游戏发烧级"}
                return textMessage;
            }
        });
    }

    @Override
    public List<Goods> findAll() {
        List<Goods> goodsList = (List<Goods>) redisTemplate.opsForHash().get("goods", "goodsList");

        if (goodsList != null && goodsList.size()>0){
            // 缓存中有数据
            System.out.println("从缓存中获取");
            return goodsList;
        }

        // 缓存中没有
        goodsList = goodsMapper.findAll();

        if (goodsList !=null && goodsList.size()>0){
            // 存入缓存
            redisTemplate.opsForHash().put("goods","goodsList",goodsList);
        }

        return goodsList;
    }

    @Override
    public List<Goods> findAllByPage(int page, int size) {
        // 设置分页信息
        PageHelper.startPage(page, size);
        // 必须保证查询语句紧跟其后size=2
        List<Goods> goodsList = goodsMapper.findAll();
        PageInfo<Goods> pageInfo = new PageInfo<Goods>(goodsList);
        System.out.println("size=" + pageInfo.getSize()); // 每页显示
        System.out.println("total" + pageInfo.getTotal()); // 总记录数
        System.out.println("nextPage" + pageInfo.getNextPage()); // 下一页
        System.out.println("PageNum" + pageInfo.getPageNum()); // 当前页
        System.out.println("getPages" + pageInfo.getPages()); // ？

        return pageInfo.getList();
    }

//    @Override
//    public void save(Goods goods) {
//        goodsMapper.save(goods);
//    }

    @Override
    public void save(Map map) {
        goodsMapper.save(map);
    }
}
