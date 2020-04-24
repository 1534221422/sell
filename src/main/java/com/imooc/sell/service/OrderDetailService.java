package com.imooc.sell.service;

import com.imooc.sell.dataobject.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findByOrderId(String orderId);
}
