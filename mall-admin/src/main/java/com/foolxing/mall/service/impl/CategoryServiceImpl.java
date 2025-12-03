package com.foolxing.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolxing.mall.mapper.CategoryMapper;
import com.foolxing.mall.mapper.CategoryMapper;
import com.foolxing.mall.pojo.Category;
import com.foolxing.mall.pojo.Category;
import com.foolxing.mall.pojo.query.CategoryQuery;
import com.foolxing.mall.pojo.query.CategoryQuery;
import com.foolxing.mall.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public IPage<Category> list(CategoryQuery categoryQuery) {
        IPage<Category> page = new Page<>(categoryQuery.getPage(), categoryQuery.getLimit());
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!ObjectUtils.isEmpty(categoryQuery.getName()), "name", categoryQuery.getName());
        categoryMapper.selectPage(page, queryWrapper);
        return page;
    }
}
