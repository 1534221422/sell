package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.util.ResultVoUtil;
import com.imooc.sell.vo.ProductInfoVo;
import com.imooc.sell.vo.ProductVo;
import com.imooc.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wxd
 * @Date 20200412
 */

@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/list")
    public ResultVo findByProductType() {

        List<ProductInfo> productInfos = productService.findUpAll();
        List<Integer> categoryTypeList = productInfos.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList1 = categoryService.findByCategoryTypeIn(categoryTypeList);


        ResultVo resultVo = new ResultVo();
        List<ProductVo> listVo = new ArrayList<ProductVo>();
        for (ProductCategory productCategory : productCategoryList1) {

            ProductVo productVo = new ProductVo();
            productVo.setProductType(productCategory.getCategoryType());
            productVo.setProductName(productCategory.getCategoryName());
            ProductInfoVo productInfoVo = new ProductInfoVo();
            List<ProductInfoVo> listInfo= new ArrayList<ProductInfoVo>();

            for(ProductInfo productInfo:productInfos){
                if (productCategory.getCategoryType()==productInfo.getCategoryType()){
                productInfoVo.setProductId(productInfo.getCategoryId());
                productInfoVo.setProductName(productInfo.getCategoryName());
                productInfoVo.setProductPrice(productInfo.getProductPrice());
                productInfoVo.setProductDescription(productInfo.getProductDescription());
                productInfoVo.setProductIcon(productInfo.getProductIcon());

                listInfo.add(productInfoVo);
                productVo.setProductFoods(listInfo);
                }
            }

            List<ProductCategory> categoryList = new ArrayList<ProductCategory>();
            categoryList.add(productCategory);
            listVo.add(productVo);
        }


//        ProductInfoVo productInfoVo = new ProductInfoVo();
//        productInfoVo.setProductId(1);
//        productInfoVo.setProductName("皮蛋瘦肉粥");
//        productInfoVo.setProductPrice(BigDecimal.valueOf(1.2));
//        productInfoVo.setProductDescription("很好喝");
//        productInfoVo.setProductIcon("htt://wwww");
//
//
//        ProductVo productVo = new ProductVo();
//        productVo.setProductName("热榜");
//        productVo.setProductType(0);
//        List<ProductInfoVo> listInfo= new ArrayList<ProductInfoVo>();
//        listInfo.add(productInfoVo);
//        productVo.setProductFoods(listInfo);
//
//        ResultVo resultVo = new ResultVo();
//        resultVo.setMsg("奥力给");
//        resultVo.setCode(2);
//        List<ProductVo> listVo = new ArrayList<ProductVo>();
//        listVo.add(productVo);
//        resultVo.setData(listVo);
//        resultVo.setCode(0);
//        resultVo.setMsg("成功");
//        resultVo.setData(listVo);


        return ResultVoUtil.success(listVo);
    }

}
