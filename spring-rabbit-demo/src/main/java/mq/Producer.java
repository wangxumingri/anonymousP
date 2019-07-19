package mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author:Created by wx on 2019/4/17
 * Desc:生产者服务
 */
@Component("producer")
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String exChange, String routingKey, Object message) {
        rabbitTemplate.convertAndSend(exChange, routingKey, message);
    }

}
