package com.foolxing.mall.service.impl;

import com.foolxing.mall.mapper.ProductMapper;
import com.foolxing.mall.pojo.Product;
import com.foolxing.mall.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-01
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
