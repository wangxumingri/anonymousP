package exchangeType.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author:Created by wx on 2019/4/16
 * Desc:交换类型为direct
 */
public class Producer {
    public static void main(String[] args) {
        try {
            // 1.创建连接工厂
            // 2.获取连接
            Connection connection = RabbitMQUtils.getConnection();
            // 3.创建信道
            Channel channel = connection.createChannel();
            // 4.声明与设置交换器
            String exChangeName = "direct-EX";
            channel.exchangeDeclare(exChangeName, "direct", true);
            // 5.设置RoutingKey
            String routingKey = "wx";
            // 6.发布消息
            byte[] messageBodyBytes = "hello,this is a message whose routingKey is wx and through by direct-EX".getBytes();
            // 7.发布消息
            channel.basicPublish(exChangeName, routingKey, null, messageBodyBytes);

            // 8.释放资源
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

}
