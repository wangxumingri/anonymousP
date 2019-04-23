package com.wxss.rabbitmqproducer.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author:Created by wx on 2019/4/17
 * Desc:
 */
@Data
public class User implements Serializable {
    private String username;
    private int age;
}
