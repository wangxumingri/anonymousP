package mapper;

import pojo.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsMapper {
    List<Goods> findAll();
    void saveGoods(Goods goods);
    void save(Map map);

    Goods findOneById(int id);
}
