package com.foolxing.mall.pojo.query;

import lombok.Data;

@Data
public class CategoryQuery {
    private Integer page;
    private Integer limit;
    private String name;
}
