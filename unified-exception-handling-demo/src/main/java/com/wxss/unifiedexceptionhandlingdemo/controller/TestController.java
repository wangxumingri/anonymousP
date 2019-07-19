package com.wxss.unifiedexceptionhandlingdemo.controller;

import com.wxss.unifiedexceptionhandlingdemo.exception.CustomeException;
import com.wxss.unifiedexceptionhandlingdemo.pojo.ResultType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:Created by wx on 2019/4/19
 * Desc:
 */
@Slf4j
@RestController
public class TestController {


    @RequestMapping("/test/{id}")
    public String test(@PathVariable("id") int id) {
        log.debug("debug");
        log.info("info");
        log.error("s:{} error括号");
        if (1 == id)
            throw new CustomeException(ResultType.ERROR);
        else {
            return "测试";
        }
    }
}
