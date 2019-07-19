package com.wxss.transactiondemo.dao;

import com.wxss.transactiondemo.pojo.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 不需注解，将该接口注册成bean
 */
public interface GoodsDao extends JpaRepository<Goods, Integer>, JpaSpecificationExecutor<Goods> {

    /*
     *  1.我们自己的接口继承spring data jpa 提供的两个接口后,
     *    可以 【直接】 使用父接口中的方法，进行SQL操作：
     *  2.我们还可以自定义方法：（符合JPA方法命名规则）
     */

}
