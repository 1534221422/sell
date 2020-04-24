package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Override
    public Page<OrderMaster> findByOrderMasterOpenid(String orderMasterOpenid, PageRequest pageRequest) {
        return orderMasterRepository.findByBuyerOpenid(orderMasterOpenid,pageRequest);
    }
}
