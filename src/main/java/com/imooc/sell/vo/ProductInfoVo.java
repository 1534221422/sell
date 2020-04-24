package com.imooc.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ Author     ：wxd.
 * @ Date       ：Created in 14:44 2020/4/12
 * @ Description：食物
 * @ Modified By：
 * @Version: 1.0$
 */
@Data
public class ProductInfoVo {
    @JsonProperty("id")
    private String ProductId;
    @JsonProperty("name")
    private String ProductName;
    @JsonProperty("price")
    private BigDecimal ProductPrice;
    @JsonProperty("description")
    private String ProductDescription;
    @JsonProperty("icon")
    private String ProductIcon;
}
