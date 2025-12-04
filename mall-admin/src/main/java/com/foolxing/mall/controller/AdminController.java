package com.foolxing.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.foolxing.mall.annotation.MyLog;
import com.foolxing.mall.pojo.Admin;
import com.foolxing.mall.pojo.Admin;
import com.foolxing.mall.pojo.dto.AdminPasswordDTO;
import com.foolxing.mall.pojo.query.AdminQuery;
import com.foolxing.mall.service.IAdminService;
import com.foolxing.mall.service.IAdminService;
import com.foolxing.mall.util.JwtUtil;
import com.foolxing.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @MyLog(module = "管理员模块")
    @GetMapping("/list")
    public Result list(AdminQuery adminQuery) {
        //PageInfo pageInfo = adminService.list(adminQuery);
        IPage<Admin> page = adminService.list(adminQuery);
        return Result.ok(page);
    }

    // /admin/deleteById?id=1
    // /admin/deleteById/1
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        adminService.removeById(id);
        return Result.ok("删除成功");
    }

    // /admin/deleteById/1,2,3
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAll(@PathVariable Integer[] ids) {
        adminService.removeBatchByIds(Arrays.asList(ids));
        return Result.ok("删除成功");
    }

    // @RequestBody : 封装前台传递过来的JSON格式数据
    // @ResponseBody : 将数据转换为JSON格式返回
    @PostMapping("/add")
    public Result add(@RequestBody Admin admin) {
        adminService.save(admin);
        return Result.ok("添加成功");
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Admin admin = adminService.getById(id);
        return Result.ok(admin);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.ok("更新成功");
    }

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", admin.getName());
        queryWrapper.eq("password", admin.getPassword());
        Admin loginAdmin = adminService.getOne(queryWrapper);
        //Admin loginAdmin = adminService.login(admin);
        if (loginAdmin == null) {
            return Result.error("用户名或密码错误");
        }

        //登录成功
        Map<String, Object> map = new HashMap<>();
        map.put("id", loginAdmin.getId());
        map.put("name", loginAdmin.getName());
        String token = JwtUtil.createToken(map);
        return Result.ok("登录成功", token);
    }

    @GetMapping("/adminInfo")
    public Result adminInfo(@RequestHeader(name = "Authorization") String token) {
        Map<String, Object> map = JwtUtil.parseToken(token);
        int id = (int) map.get("id");
        Admin admin = adminService.getById(id);
        return Result.ok(admin);
    }

    @PutMapping("/resetPassword")
    public Result resetPassword(@RequestBody AdminPasswordDTO adminPasswordDTO,
                                @RequestHeader(name = "Authorization") String token) {
        Map<String, Object> map = JwtUtil.parseToken(token);
        int id = (int) map.get("id");
        Admin admin = adminService.getById(id);
        if (!admin.getPassword().equalsIgnoreCase(adminPasswordDTO.getOldPassword())) {
            return Result.error("原密码错误");
        }

        Admin resetPasswordAdmin = new Admin();
        resetPasswordAdmin.setId(id);
        resetPasswordAdmin.setPassword(adminPasswordDTO.getNewPassword());
        adminService.updateById(resetPasswordAdmin);
        return Result.ok("修改密码成功");
    }
}

