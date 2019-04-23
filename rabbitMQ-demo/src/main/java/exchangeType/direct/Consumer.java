package exchangeType.direct;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Author:Created by wx on 2019/4/16
 * Desc:deiect的消费者
 */
public class Consumer {
    public static void main(String[] args) {
        //建立到代理服务器到连接
        Connection conn = null;
        try {
            // 获取连接
            conn = RabbitMQUtils.getConnection();
            //获得信道
            Channel channel = conn.createChannel();
            //声明交换器
            String exchangeName = "direct-EX";
            channel.exchangeDeclare(exchangeName, "direct", true);
            //声明队列
            String queueName = channel.queueDeclare().getQueue();
//            String bindingKey = "hola";
            // 设置bindingkey
            String bindingKey = "wx";
            //绑定队列，通过键 hola 将队列和交换器绑定起来
            channel.queueBind(queueName, exchangeName, bindingKey);

            while (true) {
                //消费消息
                boolean autoAck = false;
                String consumerTag = "";
                channel.basicConsume(queueName, autoAck, consumerTag, new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        System.out.println("消费的路由键：" + routingKey);
                        System.out.println("消费的内容类型：" + contentType);
                        long deliveryTag = envelope.getDeliveryTag();
                        //确认消息：false确认当前消息，true确认目前所有
                        channel.basicAck(deliveryTag, false);
                        System.out.println("消费的消息体内容：");
                        String bodyStr = new String(body, "UTF-8");
                        System.out.println(bodyStr);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

