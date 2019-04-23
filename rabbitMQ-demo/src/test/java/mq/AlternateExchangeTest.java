package mq;

import com.rabbitmq.client.*;
import org.junit.Test;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * Author:Created by wx on 2019/4/18
 * Desc:备份交换器
 */
public class AlternateExchangeTest {
    // 队列
    private static final String QUEUE_TEST1 = "queue_test1";
    private static final String QUEUE_TEST2 = "queue_test2";
    private static final String QUEUE_ALTERNATE = "queue_alternate";
    // 普通交换机
    private static final String EXCHANGE_ONE = "exchange_one";
    private static final String EXCHANGE_TWO = "exchange_two";
    // 备份交换机
    private static final String EXCHANGE_ALTERNATE = "exchange_alternate";
    // 路由
    private static final String ROUTING_1 = "routing_1";
    private static final String ROUTING_2 = "routing_2";
    private static final String ROUTING_3 = "routing_3";

    /**
     * 测试备份交换器
     */
    @Test
    public void test1() throws IOException, TimeoutException {
        // 虚拟主机下创建一个连接
        Connection connection = RabbitMQUtils.getConnection();
        // 在该连接下创建一个信道
        Channel channel = connection.createChannel();
        // 在该信道下声明队列
        Map<String, Object> argsMap = new HashMap<>();

        // 关键：设置交换器属性
        argsMap.put("alternate-exchange",EXCHANGE_ALTERNATE);
        // 声明普通交换器:为其指定备用交换器
        channel.exchangeDeclare(EXCHANGE_ONE, BuiltinExchangeType.DIRECT, false,false,argsMap);
        // 声明共用备用交换器
        channel.exchangeDeclare(EXCHANGE_ALTERNATE, BuiltinExchangeType.FANOUT,true);

        // 声明普通队列
        /**
         * durable:是否持久化
         * exclusive:是否是私有队列
         * autoDelete：是否自动删除
         * arguments:队列的其他属性
         */
        channel.queueDeclare(QUEUE_TEST1,false,false,false,null);
        // 绑定
        channel.queueBind(QUEUE_TEST1, EXCHANGE_ONE, "R1");
        // 声明备用队列
        channel.queueDeclare(QUEUE_ALTERNATE, true, false, false, null);
        channel.queueBind(QUEUE_ALTERNATE, EXCHANGE_ALTERNATE, "");
        // 发送一体不可被路由的消息
        channel.basicPublish(EXCHANGE_ONE, "R2",null,"消息不可被路由".getBytes(StandardCharsets.UTF_8) );

        AMQP.BasicProperties.Builder s = new AMQP.BasicProperties().builder().expiration("s");

        // 发送消息
        // 可路由
//        channel.basicPublish(EXCHANGE_ONE, "R1",null,"消息可被路由".getBytes(StandardCharsets.UTF_8) );
    }
}
