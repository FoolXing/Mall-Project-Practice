package com.foolxing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foolxing.mall.pojo.Product;
import com.foolxing.mall.pojo.query.ProductQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-01
 */
public interface IProductService extends IService<Product> {

    IPage<Product> list(ProductQuery productQuery);
}
