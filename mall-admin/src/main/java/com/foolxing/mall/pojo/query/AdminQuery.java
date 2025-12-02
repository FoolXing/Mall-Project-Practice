package com.foolxing.mall.pojo.query;

import lombok.Data;

@Data
public class AdminQuery {
    private Integer page;
    private Integer limit;
    private String name;
    private String email;
}
