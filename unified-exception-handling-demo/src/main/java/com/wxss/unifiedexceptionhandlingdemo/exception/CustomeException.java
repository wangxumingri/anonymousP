package com.wxss.unifiedexceptionhandlingdemo.exception;

import com.wxss.unifiedexceptionhandlingdemo.pojo.ResultType;
import lombok.Getter;
import lombok.Setter;

/**
 * Author:Created by wx on 2019/4/19
 * Desc:
 */

@Getter
@Setter
public class CustomeException extends RuntimeException{
    private int code;
    private String message;

    public CustomeException(int code,String message){
        this.code = code;
        this.message = message;
    }

    public CustomeException(ResultType resultType){
        this.message = resultType.getMsg();
        this.code = resultType.getCode();
    }
}
