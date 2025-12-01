package com.foolxing.mall.controller;


import com.foolxing.mall.pojo.Admin;
import com.foolxing.mall.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author FoolXing
 * @since 2025-12-01
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;
    @GetMapping("/list")
    public List<Admin> list (){
        return adminService.list();
    }
}

