package queue.simple;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author:Created by wx on 2019/4/16
 * Desc:没有交换器的one to one 的队列
 */
public class Producer {
    private static final String QUEUE_NAME = "simple-queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = RabbitMQUtils.getConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 声明队列
        AMQP.Queue.DeclareOk simpleQueue = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 消息体
        String message = "this is a message send to simple-queue without exchange";
        // 发送消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        // 打印提示
        System.out.println("发送消息：" + message);
    }
}
