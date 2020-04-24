package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.util.OrderStatusEnum;
import com.imooc.sell.util.PayStatusEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    /*订单id*/
    private String orderId;
    /*买家名字*/
    private String buyerName;
    /*买家电话*/
    private String buyerPhone;
    /*买家地址*/
    private String buyerAddress;
    /*买家微信openid*/
    private String buyerOpenid;
    /*订单总金额*/
    private BigDecimal orderAmount;
    /*订单状态, 默认为新下单*/
    private Integer orderStatus;
    /*支付状态*/
    private Integer payStatus;
    /*创建时间*/
    private Date createTime;
    /*修改时间*/
    private Date updateTime;
    /*订单详情*/
    List<OrderDetail> orderDetailList;
}
