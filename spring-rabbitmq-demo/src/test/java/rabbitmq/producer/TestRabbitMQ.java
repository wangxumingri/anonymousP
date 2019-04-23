//package rabbitmq.producer;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * Author:Created by wx on 2019/4/16
// * Desc:
// */
//@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(locations = {"classpath:spring/applicationContext-rabbitmq-consumer.xml","classpath:spring/applicationContext-rabbitmq-producer.xml"})
//@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
//public class TestRabbitMQ {
//
//    private static final String ROUTING_KEY1 = "que_cat_key";
//    private static final String ROUTING_KEY2 = "que_Mouse_key";
//
//    @Autowired
//    private RabbitTemplate  rabbitTemplate;
//
//    @Test
//    public void MouseSendTest(){
//        rabbitTemplate.convertAndSend(ROUTING_KEY1,"hello.tom");
//    }
//}
