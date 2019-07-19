package com.wxss.rbconsumer.service;

import com.wxss.rbconsumer.pojo.Spittle;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Author:Created by wx on 2019/4/16
 * Desc:
 */
@Component
public class ConsumerListener {
    @RabbitListener(queues = "test_simple_queue", containerFactory = "autoAckContainerFactory")
    public void onSimpleQueue(Spittle spittle) {
        System.out.println(spittle);
    }
}
