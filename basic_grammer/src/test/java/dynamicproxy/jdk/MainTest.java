package dynamicproxy.jdk;

import org.junit.Test;

/**
 * Author:Created by wx on 2019/4/23
 * Desc:
 */
public class MainTest {


    @Test
    public void testJdkProxy(){
        JdkProxy jdkProxy = new JdkProxy();
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.query();
        userDao.save();
        String rs1 = userDao.print("小米10");
        System.out.println(rs1);

        UserDao dao = (UserDao) jdkProxy.newProxy(userDao);
        dao.query();
        dao.save();
        String rs2 = dao.print("小米10");
        System.out.println(rs2);
    }
}
