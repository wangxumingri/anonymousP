package thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:Created by wx on 2019/4/23
 * Desc:测试线程
 */
public class LockDemo2 {

    static final Lock lock = new ReentrantLock();


    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    process();
                }
            }, "[Thread-" + i + "]").start();
        }
    }

    private static void process() {
        int a = 1;

        System.out.println("***************" + Thread.currentThread().getName() + "try之前：" + a + "***************");
        boolean flag = false;
        flag = lock.tryLock();

        System.out.println(Thread.currentThread().getName() + "：【" + flag + "】");
        try {
            if (flag) {
                // 获取到锁
                System.out.println(Thread.currentThread().getName() + "获取锁" + (a + 2222));
            } else {
                // 没获取锁:休眠+递归
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + "获取失败，休眠1秒,重新调用process");
                process();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (flag) {
                // 释放资源
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }
        }
    }
}
/**
 * 已经获取锁的线程不会再执行，没获取的会一直执行，直到获取锁
 */