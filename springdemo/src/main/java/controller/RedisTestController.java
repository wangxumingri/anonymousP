package controller;

import mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.Goods;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:Created by wx on 2019/4/22
 * Desc:
 */
@RestController
@RequestMapping("/redis")
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsMapper goodsMapper;

    Lock lock = new ReentrantLock();

    @RequestMapping("/findOne")
    public void findOneById(String id) {

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        process(id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "thread-" + i).start();
        }
    }

    private synchronized void process1(String id) {
        System.out.println(Thread.currentThread().getName() + "获取到锁");
        Goods goods = (Goods) redisTemplate.opsForHash().get("goods", id);
        if (goods == null) {
            System.out.println("缓存中没有，从数据库中取数据");
            goods = goodsMapper.findOneById(Integer.valueOf(id));
            if (goods != null) {
                System.out.println("更新缓存");
                redisTemplate.opsForHash().put("goods", id, goods);
            }
        }

        System.out.println("缓存中有数据"+goods.toString());
    }


    private void process(String id) throws InterruptedException {


        Goods goods = (Goods) redisTemplate.opsForHash().get("goods@id", id);
        boolean flag = false;

        System.out.println("线程"+Thread.currentThread().getName()+"try前的goods"+goods);
        try {
            if (goods == null) {
                System.out.println("线程"+Thread.currentThread().getName()+"第一个if条件前的goods"+goods);
                System.out.println("线程"+Thread.currentThread().getName()+"启动，状态["+Thread.currentThread().getState()+"]，准备占用lock"+new Date());
                // 请求锁
                 flag = lock.tryLock(3,TimeUnit.SECONDS);
                 // 占有锁才能执行if内
                if (flag) {
                    System.out.println("线程"+Thread.currentThread().getName()+"启动，状态["+Thread.currentThread().getState()+"]，成功占用lock"+new Date());
                    goods = (Goods) redisTemplate.opsForHash().get("goods@id", id);
                    System.out.println("线程"+Thread.currentThread().getName()+"第二个if条件前的goods"+goods);
                    // 再次检测redis
                    if (goods == null){
                        System.out.println("线程"+Thread.currentThread().getName()+"缓存中没有，从数据库中取数据"+new Date());
                        goods = goodsMapper.findOneById(Integer.valueOf(id));
                        System.out.println("线程"+Thread.currentThread().getName()+"数据库的goods"+goods);
                        if (goods != null) {
                            // 存入redis
                            System.out.println("线程"+Thread.currentThread().getName()+"更新了缓存"+new Date());
                            redisTemplate.opsForHash().put("goods@id", id, goods);
                        }
                    }else {
                        System.out.println("线程"+Thread.currentThread().getName()+"第二次检测发现redis中有数据"+goods);
                    }
                } else {
                    System.out.println("线程"+Thread.currentThread().getName()+"启动，状态["+Thread.currentThread().getState()+"]，请求锁失败，暂停2000ms,再次请求..."+new Date());
                    // 请求失败，暂停100ms
                    Thread.sleep(4000);

//                    process(id);
                }
            }else {
                System.out.println("线程"+Thread.currentThread().getName()+"从缓存中获取数据" + goods.toString()+new Date());
            }
        } catch (InterruptedException | NumberFormatException e) {
            e.printStackTrace();
        } finally {
            // 释放锁：没占用，却释放会报错
            if (flag){
                System.out.println("线程"+Thread.currentThread().getName()+"启动，状态["+Thread.currentThread().getState()+"]，释放锁..."+new Date());
                lock.unlock();
            }
        }
    }

    /**
     * 测试缓存穿透
     * @param id
     * @return
     */
    @RequestMapping("/test3")
    public void test3(String id){
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    process3(id);
                }
            }).start();
        }
    }

    void process3(String id){
        Goods goods = null;
        boolean b =false;
        try {
            goods = (Goods) redisTemplate.opsForHash().get("goods", id);

            if (goods == null){
                b = lock.tryLock();
                System.out.println(Thread.currentThread().getName()+"缓存无数据");
                System.out.println(Thread.currentThread().getName()+"是否成功获取锁:"+b);

                if (b){
                    // query db
                    goods = goodsMapper.findOneById(Integer.valueOf(id));
                    if (goods == null){
                        // data of db is null, save to cache
                        redisTemplate.opsForHash().put("goods", id, null);
                        System.out.println(Thread.currentThread().getName()+"数据库为null，缓存空值");
                    }else {
                        redisTemplate.opsForHash().put("goods", id, goods);
                        System.out.println(Thread.currentThread().getName()+"数据库有数据，存入缓存");
                    }
                }else {
                    System.out.println(Thread.currentThread().getName()+"获取锁失败");
                    Thread.sleep(1000);
                    process3(id);
                }
            }else {
                System.out.println(Thread.currentThread().getName()+"缓存存在");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (b){
                lock.unlock();
                System.out.println(Thread.currentThread().getName()+"释放锁");
            }
        }

        System.out.println(Thread.currentThread().getName()+goods);
    }
}
