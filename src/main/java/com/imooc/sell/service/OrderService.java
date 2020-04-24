package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;
import org.springframework.data.domain.PageRequest;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderService {
    /*创建订单*/
     OrderDTO create(OrderDTO orderDTO);
    /*查询单个订单*/
    OrderDTO findById(String orderId);
    /*查询订单列表*/
    List<OrderDTO> findOrderMaster(String orderId, PageRequest pageable);
    /*关闭订单*/
    OrderDTO cancle(OrderDTO orderDTO);
    /*支付订单*/
    OrderDTO payOrder(OrderDTO orderDTO);
    /*完结订单*/
    OrderDTO finish(OrderDTO orderDTO);
    /*查询所有订单*/
    List<OrderDTO> findAllorderMaste(PageRequest pageRequest);
}
