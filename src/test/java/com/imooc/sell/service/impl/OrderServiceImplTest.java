package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    OrderService orderService;

    private final String BUYER_OPENID = "1110110";

    private final String ORDER_ID = "1586951390291233835";
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("11111");
        orderDTO.setBuyerName("11111");
        orderDTO.setBuyerOpenid("BUYER_OPENID");
        orderDTO.setBuyerPhone("15611180151");
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("time1");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("time1");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);
        orderService.create(orderDTO);
    }

    @Test
    public void findById() {
    }

    @Test
    public void findOrderMaster() {
        orderService.findAllorderMaste(PageRequest.of(0,2));
    }

    @Test
    public void cancle() {
        OrderDTO orderDTO = orderService.findById(ORDER_ID);
        OrderDTO result = orderService.cancle(orderDTO);
    }

    @Test
    public void payOrder() {
        OrderDTO orderDTO = orderService.findById(ORDER_ID);
        orderService.payOrder(orderDTO);
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findById(ORDER_ID);
        orderService.finish(orderDTO);
    }
}