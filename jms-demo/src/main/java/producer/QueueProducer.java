package producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * queue消息的生产者
 */
public class QueueProducer {
    public static void main(String[] args) throws JMSException {
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
        // 创建生产者
        MessageProducer producer = session.createProducer(queue);
        // 创建消息对象
        TextMessage textMessage = session.createTextMessage("hello,i am producer");
        // 发送消息
        producer.send(textMessage);

        // 释放资源
        producer.close();
        session.close();
        connection.close();
    }
}
