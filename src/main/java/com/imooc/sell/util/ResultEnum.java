package com.imooc.sell.util;

public enum ResultEnum {
    PRODUCT_NOT_EXIST(0,"商品不存在"),
    PRODUCT_NOT_ERRRO(1,"商品无库存"),
    PRODUCT_STATUS_ERROR(3,"商品状态异常"),
    PRODUCT_UPDATE_ERROR(4,"商品更新出现异常"),
    ORDER_PAY_ERROR(5,"订单支付状态有误"),
    ORDER_UPDATE_ERROR(6,"订单更新失败"),
    ORDERMASTER_OPENID_ERROR(7,"清单openid有误"),
    ORDER_OPENID_ERROR(8,"用户openid有误")
    ;
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

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
