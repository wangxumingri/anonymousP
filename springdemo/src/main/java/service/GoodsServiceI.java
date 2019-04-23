package service;

import pojo.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsServiceI {

    /**
     * 查询所有：未分页
     * @return
     */
    List<Goods> findAll();

    /**
     * 查询所有：分页
     * @return
     */
    List<Goods> findAllByPage(int page,int size);

//    /**
//     * 保存商品
//     * @param goods
//     */
    void save(Goods goods);

    void save(Map map);
}
