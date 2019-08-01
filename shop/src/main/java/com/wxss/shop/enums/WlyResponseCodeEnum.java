package com.wxss.shop.enums;

/**
 * Author:Created by wx on 2019/8/1
 * Desc:
 */
public enum WlyResponseCodeEnum {
    SUCCESS(0, "Success"),
    PARAMETER_ERROR(100, "参数错误"),
    PARAMETER_FORMAT_ERROR(101, "参数格式错误"),
    TOKEN_ERROR(102, "令牌错误"),
    NODATA(103, "数据不存在"),
    SYSTEM_BUSY(104, "系统繁忙"),
    POST_DATA_ERRPR(1000,"post请求体错误");

    private int code;
    private String message;

    WlyResponseCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
