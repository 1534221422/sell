package com.imooc.sell.util;

import lombok.Getter;

@Getter
public enum  OrderStatusEnum {

    NEW(0,"新订单"),
    FINISH(1,"订单完成"),
    CANCLE(2,"订单取消");

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
