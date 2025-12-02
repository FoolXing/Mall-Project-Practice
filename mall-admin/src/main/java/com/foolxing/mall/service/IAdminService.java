package com.foolxing.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.foolxing.mall.pojo.Admin;
import com.foolxing.mall.pojo.query.AdminQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-01
 */
public interface IAdminService extends IService<Admin> {

    IPage<Admin> list(AdminQuery adminQuery);
}
