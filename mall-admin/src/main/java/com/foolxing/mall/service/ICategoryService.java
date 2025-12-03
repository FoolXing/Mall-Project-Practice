package com.foolxing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foolxing.mall.pojo.Category;
import com.foolxing.mall.pojo.query.CategoryQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-01
 */
public interface ICategoryService extends IService<Category> {

    IPage<Category> list(CategoryQuery categoryQuery);
}
