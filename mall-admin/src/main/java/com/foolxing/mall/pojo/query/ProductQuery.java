package com.foolxing.mall.pojo.query;

import lombok.Data;

@Data
public class ProductQuery {
    private Integer page;
    private Integer limit;
    private String name;
    private Integer categoryId;
}
