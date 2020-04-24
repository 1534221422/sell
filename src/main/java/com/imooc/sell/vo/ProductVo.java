package com.imooc.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Data;

import java.util.List;

/**
 * @ Author     ：wxd.
 * @ Date       ：Created in 14:37 2020/4/12
 * @ Description：产品描述
 * @ Modified By：
 * @Version: 1.0$
 */
@Data
public class ProductVo {
    /*成品描述*/
    @JsonProperty("name")
    private String ProductName;
    @JsonProperty("type")
    private Integer ProductType;
    @JsonProperty("foods")
    private List<ProductInfoVo> ProductFoods;

}
