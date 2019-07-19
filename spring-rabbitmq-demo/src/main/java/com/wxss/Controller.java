package com.wxss;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:Created by wx on 2019/4/16
 * Desc:
 */
@RestController
@RequestMapping("/producer")
public class Controller {

//    @Autowired
//    private RabbitTemplate rabbitTemplate;

    //    @RequestMapping(value = "/test1" ,method = RequestMethod.POST)
    @RequestMapping(value = "/test1", produces = "application/json;charset=UTF-8")
    public String test1() {
//        String message = "少时诵诗书";
//        rabbitTemplate.convertAndSend("que_cat_key",message);

        return "发送成功";
    }


    //    @RequestMapping(value = "/test2",produces = "application/json;charset=UTF-8")
    @RequestMapping(value = "/test2")
    public String test2() {
        User user = new User();
        user.setAge(212);
        user.setName("手打");

//        rabbitTemplate.convertAndSend("que_pig_key",user);

        return "发送成功";
    }


}
