package com.imooc.sell.exception;

import com.imooc.sell.util.ResultEnum;

public class SellException extends RuntimeException {

    private  Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
