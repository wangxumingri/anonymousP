package controller;


import config.ResultConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pojo.Goods;
import pojo.Result;
import service.GoodsServiceI;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsServiceI goodsService;

    private Logger logger = Logger.getLogger(GoodsController.class);


    @RequestMapping("/testLogger")
    public String testLogger(){
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");

        return "测试log";
    }


    @GetMapping(value = "/findAll",produces = "application/json;charset=utf8")
    public Result findAll(){
        List<Goods> goodsList = goodsService.findAll();
        logger.info("info");

        if (goodsList!=null && goodsList.size()>0){
            System.out.println(goodsList.get(1));
            System.out.println(goodsList.size());
            return new Result<List<Goods>>(true, ResultConfig.SUCCESS_MESSAGE,goodsList);
        }
        logger.debug("debug");
        logger.trace("trace");
        logger.error("error");
        return new Result(false,ResultConfig.FAILED_MESSAGE);
    }

    @GetMapping(value = "/findAllByPage",produces = "application/json;charset=utf8")
    public Result findAllByPage(@RequestParam(name="p",defaultValue = "4") int page, @RequestParam(defaultValue = "1",required = false) int size){
        List<Goods> goodsList = goodsService.findAllByPage(page,size);


        if (goodsList!=null && goodsList.size()>0){
            System.out.println(goodsList.size());
            return new Result<List<Goods>>(true, ResultConfig.SUCCESS_MESSAGE,goodsList);
        }

        return new Result(false,ResultConfig.FAILED_MESSAGE);
    }


    @RequestMapping("/save")
    public void saveGoods(){
        Goods goods = new Goods();
        goods.setGoodsName("未来笔记本电脑");
        goods.setTitle("游戏发烧级");
        goods.setPrice(BigDecimal.valueOf(99999.9));
        goods.setGoodsdesc("游戏电脑");

        goodsService.save(goods);

    }

    @RequestMapping("/saveMap")
    public void saveGoodsMap(){

        Goods goods = new Goods("aa11414aa","sdaa",BigDecimal.valueOf(9995.9),"ss");
//        Goods goods = new Goods();
//        goods.setGoodsName("kkkk");
//        goods.setTitle("工作利器");
//        goods.setPrice(BigDecimal.valueOf(9999.9));
//        goods.setGoodsdesc("私人电脑");

        Map map = new HashMap();
        map.put("cs",goods);

        goodsService.save(map);

    }
}
