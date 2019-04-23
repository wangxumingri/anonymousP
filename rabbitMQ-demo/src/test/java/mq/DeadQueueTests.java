package mq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:Created by wx on 2019/4/18
 * Desc:
 */
public class DeadQueueTests {
    ConnectionFactory connectionFactory = null;
    Connection conn = null;
    Channel channel = null;

    @Before
    public void before(){
        System.out.println("*************初始化**************");
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("test-host1");

        try {
            conn = connectionFactory.newConnection();
            channel = conn.createChannel();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    @After
    public void after(){
        System.out.println("*************释放资源**************");
        if (channel != null){
            try {
                channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 死信队列测试
     */
    @Test
    public void testDeadQueue() throws IOException {
        channel.exchangeDeclare("普通交换器", BuiltinExchangeType.DIRECT, true);
        channel.exchangeDeclare("死信交换器", BuiltinExchangeType.DIRECT, true);

        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl",10000);// 10秒过期
        args.put("x-dead-letter-exchange","死信交换器");// 指定死信交换器的名称
        args.put("x-dead-letter-routing-key","dk");// 死信交换器的路由

        channel.queueDeclare("普通队列", true, false,false, args);
        channel.queueDeclare("死信队列", true, false,false, null);

        channel.queueBind("普通队列", "普通交换器", "pk");
        channel.queueBind("死信队列", "死信交换器", "dk");
        // 消息首先会发生到普通队列，10秒后，消息过期，成为死信，自动移动到死信队列
        channel.basicPublish("普通交换器", "pk", null, "普通交换器和普通队列:10秒后队列过期".getBytes(StandardCharsets.UTF_8));
    }

}
