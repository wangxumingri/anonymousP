package com.wxss.rabbitmqproducer.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author:Created by wx on 2019/4/17
 * Desc:
 */
@Configuration
public class RabbitmqConfig {
    /**
     * 邮件队列
     */
    public static final String QUEUE_EMAIL = "queue_email";
    /**
     * 短信队列
     */
    public static final String QUEUE_SMS = "queue_sms";
    /**
     * direct类型的交换机
     */
    public static final String EXCHANGE_DIRECT = "exchange_direct";
    /**
     * 邮件队列的RoutingKey
     */
    public static final String ROUTING_EMAIL = "routing_email";
    /**
     * 短信队列的RoutingKey
     */
    public static final String ROUTING_SMS = "routing_sms";

    // 连接工厂属性
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    /**
     * Rb的连接工厂
     *
     * @return
     */
//    @Bean
//    public ConnectionFactory connectionFactory(){
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost(host);
//        connectionFactory.setPort(port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost(virtualHost);
//        return connectionFactory;
//    }
//
//    @Bean
//    public RabbitAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }
//
//    @Bean("myTemplate")
//    public RabbitTemplate rabbitTemplate(){
//        RabbitTemplate rabbitTemplate = new RabbitTemplate();
//        rabbitTemplate.setConnectionFactory(connectionFactory());
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//
//        return rabbitTemplate;
//    }

//     序列化方式
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    /**
     * 声明邮件队列
     *
     * @return
     */
    @Bean(QUEUE_EMAIL)
    public Queue QUEUE_EMAIL() {
        return new Queue(QUEUE_EMAIL);
    }

    /**
     * 声明短信队列
     *
     * @return
     */
    @Bean(QUEUE_SMS)
    public Queue QUEUE_SMS() {
        return new Queue(QUEUE_SMS);
    }

    /**
     * 声明direct交换机
     *
     * @return
     */
    @Bean(EXCHANGE_DIRECT)
    public Exchange EXCHANGE_DIRECT() {
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT).durable(false).build();
    }

    /**
     * 将SMS队列绑定到direct交换机上，并指定路由
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_SMS(@Qualifier(QUEUE_SMS) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_SMS).noargs();
    }

    /**
     * 将EMAIL队列绑定到direct交换机上，并指定路由
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_EMAIL(@Qualifier(QUEUE_EMAIL) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_EMAIL).noargs();
    }
}
