package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Data
public class ProductCategory {

    public ProductCategory(){}
//    类目id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
//    类目名字
    private String categoryName;
//    类目编号
    @Column
    private Integer categoryType;
//    创建时间

}
