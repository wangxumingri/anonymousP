package mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Author:Created by wx on 2019/4/17
 * Desc:rabbitMQ自身的事务与消息确认
 */
public class TransactionAndConfirmTests {
    private ConnectionFactory connectionFactory = null;
    Connection conn = null;
    Channel channel = null;

    // 事务测试的队列
    private static final String QUEUE_TX = "queue_tx";
    // confirm测试的队列
    private static final String QUEUE_CONFIRM1 = "queue_confirm1";
    private static final String QUEUE_CONFIRM2 = "queue_confirm2";
    private static final String QUEUE_CONFIRM3 = "queue_confirm3";

    @Before
    public void before() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        System.out.println("*************释放资源**************");
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void conFirmTest1() throws Exception {
        //
        channel.confirmSelect();
        // 声明队列
        channel.queueDeclare(QUEUE_CONFIRM1, false, false, false, null);
        // 发送消息
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            String message = "发送第" + (i + 1) + "条消息";
            /**
             * 参数1:交换机,如果没有声明交换机，可传 "" ,""是EB的一个默认交换机
             * 参数2：路由,如果没有路由（即默认交换机），可传递队列名
             * 参数3：基础属性
             * 参数4：发送的消息体
             * */
            channel.basicPublish("", QUEUE_CONFIRM1, null, message.getBytes());
            System.out.println(message);
            if (channel.waitForConfirms()) {
                System.out.println("消息发送成功");
            } else {
                System.out.println("消息发送失败");
            }
        }

        System.out.println("耗费时间" + (System.currentTimeMillis() - start) + "ms");
    }


    @Test
    public void conFirmTest2() throws Exception {
        //
        channel.confirmSelect();
        // 声明队列
        channel.queueDeclare(QUEUE_CONFIRM2, false, false, false, null);
        // 发送消息
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            String message = "发送第" + (i + 1) + "条消息";
            /**
             * 参数1:交换机,如果没有声明交换机，可传 "" ,""是EB的一个默认交换机
             * 参数2：路由,如果没有路由（即默认交换机），可传递队列名
             * 参数3：基础属性
             * 参数4：发送的消息体
             * */
            channel.basicPublish("", QUEUE_CONFIRM2, null, message.getBytes());
//            System.out.println(message);
        }
        // 直到所有信息都发布，才返回true，只要有一个未确认，就会IO异常
        channel.waitForConfirmsOrDie();
        System.out.println("全部执行完成，共耗费时间" + (System.currentTimeMillis() - start) + "ms");
    }


    @Test
    public void txTest() {
        try {
            // 开启事务
            channel.txSelect();
            // 声明队列：队列没被回滚
            channel.queueDeclare(QUEUE_TX, false, false, false, null);
            // 发送消息
            for (int i = 0; i < 10; i++) {
                String message = "发送第" + (i + 1) + "条消息";
                channel.basicPublish("", QUEUE_TX, null, message.getBytes());
                System.out.println(message);
            }
            // 异常
            int i = 1 / 0;
            // 提交事务
            channel.txCommit();
        } catch (IOException e) {
            try {
                channel.txRollback();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
