package dynamicproxy.cglib;

/**
 * Author:Created by wx on 2019/4/23
 * Desc:cglib动态代理
 */
public class Play {
    String playGame(String a){
        System.out.println("原来方法");
        return a;
    }
}
