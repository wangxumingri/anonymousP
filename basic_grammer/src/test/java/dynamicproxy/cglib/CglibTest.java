package dynamicproxy.cglib;

import org.junit.Test;

/**
 * Author:Created by wx on 2019/4/23
 * Desc:
 */
public class CglibTest {

    @Test
    public void testCglib(){
        CgilbProxy cgilbProxy = new CgilbProxy();
        Play playProxy = (Play) cgilbProxy.getCglibProxy(new Play());

        String lol = playProxy.playGame("LOL");
        System.out.println(lol);
    }
}
