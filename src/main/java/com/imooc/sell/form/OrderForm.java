package com.imooc.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class OrderForm {

    /*买家姓名*/
//    @NotNUll（message = "姓名必填"）
    private String name;
    /*买家地址*/
    private String address;
    /*买家电话*/
    private String phone;
    /*买家id*/
    private String openid;
    /*购物车*/
    private String items;
}
