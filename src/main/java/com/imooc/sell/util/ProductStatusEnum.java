package com.imooc.sell.util;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {

    UP(0,"在架"),
    DOWN(0,"下架");
    private  Integer code;
    private  String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

     ProductStatusEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
