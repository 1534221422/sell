package com.imooc.sell.util;

import lombok.Getter;

@Getter
public enum PayStatusEnum {

    SUCCESS(1,"已支付"),
    WAIT(0,"未支付"),
    ERROR(2,"支付失败");


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

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
