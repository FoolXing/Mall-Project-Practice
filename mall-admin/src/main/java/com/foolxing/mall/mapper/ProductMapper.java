package com.foolxing.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.foolxing.mall.pojo.Product;
import com.foolxing.mall.pojo.VO.ProductVO;
import com.foolxing.mall.pojo.query.ProductQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-01
 */
@Repository
public interface ProductMapper extends BaseMapper<Product> {


    IPage<ProductVO> list(IPage<ProductVO> page, ProductQuery productQuery);
}
