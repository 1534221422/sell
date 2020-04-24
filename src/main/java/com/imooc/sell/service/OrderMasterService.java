package com.imooc.sell.service;

import com.imooc.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface OrderMasterService {
    Page<OrderMaster> findByOrderMasterOpenid(String orderMasterOpenid, PageRequest pageRequest);
}
