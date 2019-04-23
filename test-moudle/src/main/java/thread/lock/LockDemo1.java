package thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author:Created by wx on 2019/4/22
 * Desc:
 */
public class LockDemo1 {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 3; i++) {
            new Thread("[线程-"+i+"]"){
                @Override
                public void run() {
                    boolean flag =false;
                    System.out.println("线程"+Thread.currentThread().getName()+"启动，状态["+Thread.currentThread().getState()+"]，准备占用lock");

                    try {
                        flag = lock.tryLock(5, TimeUnit.SECONDS);
                        System.out.println(flag);
                        // 判断是否占用成功
                        if (flag){
                            System.out.println("线程"+Thread.currentThread().getName()+"，状态["+Thread.currentThread().getState()+"]，成功占用lock");
                            // 休眠10秒
                            Thread.sleep(5000);
                        }else {
                            System.out.println("线程"+Thread.currentThread().getName()+"，状态["+Thread.currentThread().getState()+"]，占用lock失败");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        if (flag){
                            // 占用成功，业务结束后，释放锁
                            System.out.println("线程"+Thread.currentThread().getName()+"，状态["+Thread.currentThread().getState()+"]，业务结束，释放锁");
                        }
                    }
                }
            }.start();
        }
    }
}
