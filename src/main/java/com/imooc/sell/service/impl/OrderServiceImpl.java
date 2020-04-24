package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDTO;
import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRepository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.KeyUtil;
import com.imooc.sell.util.OrderStatusEnum;
import com.imooc.sell.util.PayStatusEnum;
import com.imooc.sell.util.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDetailRepository orderDetailRepository;
    @Autowired
    OrderMasterRepository orderMasterRepository;
    @Autowired
    ProductInfoRepository productInfoRepository;
    @Autowired
    ProductService productService;

    @Override
    @Transactional
    /*查询 商品数量和价格*/
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal bigDecimal = new BigDecimal(BigInteger.ZERO);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoRepository.findById(orderDetail.getProductId()).orElse(null);
            if (null == productInfo) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            /*查询订单商品总价*/
            bigDecimal = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(bigDecimal);

            /*保存订单详情信息*/

            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
            /*保存订单*/
            OrderMaster orderMaster = new OrderMaster();
            orderDTO.setOrderId(orderId);
            BeanUtils.copyProperties(orderDTO, orderMaster);
            orderMaster.setOrderAmount(bigDecimal);
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
            orderMasterRepository.save(orderMaster);
            /*扣除库存*/
            CartDTO cartDTO = new CartDTO();
            cartDTO.setProductId(orderDetail.getProductId());
            cartDTO.setProductQuantity(orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }


        productService.increaseStock(cartDTOList);
        ;
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO findById(String orderId) {
        OrderDTO orderDTO = new OrderDTO();
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).orElse(null);
        if (null == orderMaster) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        BeanUtils.copyProperties(orderMaster, orderDTO);
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderMaster.getOrderId());
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    @Transactional
    public List<OrderDTO> findOrderMaster(String buyerOpenid, PageRequest pageable) {
//        PageRequest request = PageRequest.of(0,2);
        List<OrderMaster> orderMasterList = new ArrayList<>();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        orderMasterList = page.toList();
        for(OrderMaster orderMaster:orderMasterList){
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster,orderDTO);
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    @Override
    @Transactional
    public OrderDTO cancle(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())){
            log.error("[取消订单]订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        /*更改订单状态*/
        orderDTO.setOrderStatus(OrderStatusEnum.CANCLE.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
        if(null == orderMasterResult){
            log.error("[取消订单]订单更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.PRODUCT_UPDATE_ERROR);
        }
        List<CartDTO> cartDTOList = new ArrayList<>();
        /*修改库存*/
        List<OrderDetail>orderDetailList = orderDTO.getOrderDetailList();
        for(OrderDetail orderDetail:orderDetailList){
            CartDTO cartDTO = new CartDTO();
            cartDTO.setProductId(orderDetail.getProductId());
            cartDTO.setProductQuantity(orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }
        productService.decreaseStock(cartDTOList);
//        已支付需要退款TODO
        return null;
    }

    @Override
    @Transactional
    public OrderDTO payOrder(OrderDTO orderDTO) {
       if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())){
           log.error("[支付订单] 订单状态有误，orderStatue={}",orderDTO.getOrderStatus());
           throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        if(!PayStatusEnum.WAIT.getCode().equals(orderDTO.getPayStatus())){
            log.error("[支付订单] 订单支付状态有误 PayStatue={}",orderDTO.getPayStatus());
            throw  new SellException(ResultEnum.ORDER_PAY_ERROR);
        }
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster  = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
        if(null == orderMasterResult){
            log.error("[支付订单] 订单支付状态更新失败 orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return null;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        System.out.println("orderDTO.getOrderStatus() = " + orderDTO.getOrderStatus());
      if(OrderStatusEnum.FINISH.getCode().equals(orderDTO.getOrderStatus())){
          log.error("[订单完结] 订单状态不正确 orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
          throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
      }
      orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
      OrderMaster orderMaster = new OrderMaster();
      BeanUtils.copyProperties(orderDTO,orderMaster);
      OrderMaster orderMasterResult = orderMasterRepository.save(orderMaster);
      if(null == orderMasterResult){
          log.error("[订单完结]订单更新失败 orderStatue={}",orderMaster.getOrderStatus());
          throw new SellException(ResultEnum.PRODUCT_UPDATE_ERROR);
      }

        return orderDTO;
    }

    @Override
    @Transactional
    public List<OrderDTO> findAllorderMaste(PageRequest pageRequest) {
        
        Page<OrderMaster> orderMasterList = orderMasterRepository.findAll(pageRequest);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(OrderMaster orderMaster:orderMasterList.toList()){
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(orderMaster,orderDTO);
            orderDTOS.add(orderDTO);
        }
                
        return orderDTOS;
    }
}
