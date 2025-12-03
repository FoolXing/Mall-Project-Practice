package com.foolxing.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolxing.mall.mapper.ProductMapper;
import com.foolxing.mall.mapper.ProductMapper;
import com.foolxing.mall.pojo.Product;
import com.foolxing.mall.pojo.Product;
import com.foolxing.mall.pojo.VO.ProductVO;
import com.foolxing.mall.pojo.query.ProductQuery;
import com.foolxing.mall.pojo.query.ProductQuery;
import com.foolxing.mall.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ProductMapper productMapper;

    @Override
    public IPage<ProductVO> list(ProductQuery productQuery) {
        IPage<ProductVO> page = new Page<>(productQuery.getPage(), productQuery.getLimit());
        IPage<ProductVO> list = productMapper.list(page, productQuery);
        return list;
    }
}
