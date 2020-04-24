package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.util.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    OrderService orderService;
    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOpenid(openid,orderId);
    }
    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        return orderService.cancle(checkOpenid(openid,orderId));
    }
    private OrderDTO checkOpenid(String openid,String orderId){
        OrderDTO orderDTO = orderService.findById(orderId);
        if(!openid.equals(orderDTO.getBuyerOpenid())){
            log.error("[订单详情] 订单查询openid不一致 openid={}",openid);
            throw new SellException(ResultEnum.ORDER_OPENID_ERROR);
        }
        return orderDTO;
    }
}
