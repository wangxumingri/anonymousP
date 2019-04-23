package dynamicproxy.jdk;

/**
 * Author:Created by wx on 2019/4/23
 * Desc:
 */
public interface UserDao {
    void save();
    void query();
    String print(String param);
}
