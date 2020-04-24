package com.imooc.sell.vo;

import lombok.Data;

import java.util.List;

/**
 * @author wxd
 * @Date 20200412
 *
 */
@Data
public class ResultVo<T> {
    private Integer code;
    private String msg;
    private T data;

}
