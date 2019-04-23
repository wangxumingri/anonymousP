package com.wxss.unifiedexceptionhandlingdemo.exception;

import com.wxss.unifiedexceptionhandlingdemo.pojo.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Author:Created by wx on 2019/4/19
 * Desc:
 */
@RestControllerAdvice
public class MyExceptionHandler {

    /**
     * 所有CustomeException异常都会被这个方法捕获，处理
     * @param e 自定义异常
     * @return 自己封装的结果类
     */
    @ExceptionHandler(CustomeException.class)
    public ResponseResult handlerCustomeException(CustomeException e){
        return new ResponseResult(e.getCode(),e.getMessage());
    }
}
