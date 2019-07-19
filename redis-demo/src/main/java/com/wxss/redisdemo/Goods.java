package com.wxss.redisdemo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * Author:Created by wx on 2019/7/9
 * Desc:
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private String name;
    private int price;
//    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone = "GMT+8")
    private Date produceDate;
}
