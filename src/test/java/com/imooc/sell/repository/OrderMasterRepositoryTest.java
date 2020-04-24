package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Test
    public void findByOrderBuyerOpenId() throws Exception{
        PageRequest pageRequest =  PageRequest.of(0,2);
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid("1110110",pageRequest);
//        List<OrderMaster> list = page.toList();
        Iterator it =  page.iterator();

        while (it.hasNext()){
           OrderMaster orderMaster = (OrderMaster) it.next();
            System.out.println(" = " +  orderMaster.toString());
        }
//        if(list.size()>=0){
//            for (OrderMaster orderMaster:list){
//                System.out.println("orderMaster = " + orderMaster.getBuyerOpenid());
//            }}

        }
    }


