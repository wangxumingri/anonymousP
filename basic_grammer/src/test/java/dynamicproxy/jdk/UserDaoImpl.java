package dynamicproxy.jdk;

/**
 * Author:Created by wx on 2019/4/23
 * Desc:
 */
public class UserDaoImpl implements UserDao{
    @Override
    public void save() {
        System.out.println("run save method");
    }

    @Override
    public void query() {
        System.out.println("run query method");
    }

    @Override
    public String print(String param) {
        System.out.println("打印参数");
        return param;
    }
}
