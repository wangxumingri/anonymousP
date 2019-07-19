package consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 *
 */
public class QueueConsumer2 {
    public static void main(String[] args) throws JMSException, IOException {
        // 连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        // 创建连接
        Connection connection = connectionFactory.createConnection();
        // 开启连接
        connection.start();
        // 创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 创建目标对象
        Queue queue = session.createQueue("test-queue");
        // 创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        // 接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 等待消息
        System.in.read();

        // 释放资源
        consumer.close();
        session.close();
        connection.close();
    }
}
