package com.foolxing.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.foolxing.mall.mapper.AdminMapper;
import com.foolxing.mall.pojo.Admin;
import com.foolxing.mall.pojo.query.AdminQuery;
import com.foolxing.mall.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public IPage<Admin> list(AdminQuery adminQuery) {
        IPage<Admin> page = new Page<>(adminQuery.getPage(), adminQuery.getLimit());
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!ObjectUtils.isEmpty(adminQuery.getName()), "name", adminQuery.getName());
        queryWrapper.like(!ObjectUtils.isEmpty(adminQuery.getEmail()), "email", adminQuery.getEmail());
        adminMapper.selectPage(page, queryWrapper);
        return page;
    }
}
