package com.wxss.unifiedexceptionhandlingdemo.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * Author:Created by wx on 2019/4/19
 * Desc:
 */
@Getter
public enum ResultType {
    SUCCESS(0,"操作成功"),
    EMPTY(1,"空数据"),
    ERROR(2,"错误");

    private int code;
    private String msg;

    ResultType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }}
