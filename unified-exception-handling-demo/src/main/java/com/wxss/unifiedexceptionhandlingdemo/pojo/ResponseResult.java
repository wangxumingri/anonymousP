package com.wxss.unifiedexceptionhandlingdemo.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Author:Created by wx on 2019/4/19
 * Desc: 响应结果类
 */
@Getter
@Setter
public class ResponseResult<T> {
    private int code;
    private String msg;
    private T data;

    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(int code, String msg) {
        this(code, msg, null);
    }
}
