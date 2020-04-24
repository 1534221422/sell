package com.imooc.sell.controller;

import com.imooc.sell.converter.OrderForm2OrderDTOConverter;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import com.imooc.sell.service.OrderDetailService;
import com.imooc.sell.service.OrderMasterService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.impl.BuyerServiceImpl;
import com.imooc.sell.util.ResultEnum;
import com.imooc.sell.util.ResultVoUtil;
import com.imooc.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    OrderMasterService orderMasterService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderService orderService;
    @Autowired
    BuyerServiceImpl buyerService;

    @GetMapping(value = "/openid")
    public  void findByOrderMasterOpenid(){
        PageRequest request = PageRequest.of(0,2);
       Page<OrderMaster> pageMaster =  orderMasterService.findByOrderMasterOpenid("1110110",request);
       Iterator it = pageMaster.iterator();
       while (it.hasNext()){
        OrderMaster orderMaster =    (OrderMaster)it.next();

        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderMaster.getOrderId());
        for (OrderDetail orderDetail:orderDetails){
            System.out.println(" = " + orderDetail.toString());
        }
       }
    }
    @PostMapping(value = "/create")
    public ResultVo<Map<String,String>> create(@Validated OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("[创建订单] 参数不争取，orderForm={}",orderForm );
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单] 购物车为空 orderDetail={}",orderDTO.getOrderDetailList());
        }
        OrderDTO orderDTO1 = orderService.create(orderDTO);
        Map<String,String> map  = new HashMap<>();
        map.put("orderId",orderDTO1.getOrderId());


        return ResultVoUtil.success(map);
    }
    @RequestMapping(value="/list")
    public ResultVo<List<OrderDTO>> findOrderMaster(@RequestParam("openid") String openid,
                                                       @RequestParam(value = "page",defaultValue = "0") Integer page,
                                                       @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(StringUtils.isEmpty(openid)){
            log.error("[订单列表] 查询openid为空 openid={}",openid);
            throw new SellException(ResultEnum.ORDERMASTER_OPENID_ERROR);
        }
        PageRequest request = PageRequest.of(page,size);
        Page<OrderMaster> orderMasterResult =orderMasterService.findByOrderMasterOpenid(openid,request);
        List<OrderMaster> orderMasterList = orderMasterResult.toList();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        OrderDTO orderDTO = new OrderDTO();
        for(OrderMaster orderMaster:orderMasterList){
            BeanUtils.copyProperties(orderMaster,orderDTO);
            orderDTOList.add(orderDTO);
        };
       return ResultVoUtil.success(orderDTOList);
    }
    @RequestMapping(value = "/detail")
    public ResultVo<List<OrderDTO>> findDetail(@RequestParam("openid") String openid,
                                               @RequestParam("orderId") String orderId){
        return ResultVoUtil.success(buyerService.findOrderOne(openid,orderId));
    }
    @RequestMapping(value = "/cancel")
    public ResultVo<List<OrderDTO>> cancel(@RequestParam("openid") String openid,
                                               @RequestParam("orderId") String orderId){
        return ResultVoUtil.success(buyerService.cancelOrder(openid,orderId));
    }
}
