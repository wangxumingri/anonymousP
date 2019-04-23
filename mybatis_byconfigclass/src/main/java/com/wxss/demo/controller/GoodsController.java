package com.wxss.demo.controller;

import com.wxss.demo.entity.Goods;
import com.wxss.demo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private Service service;

    @GetMapping("/findAll")
    public List<Goods> findAll(){
        return service.findAll();
    }
}
