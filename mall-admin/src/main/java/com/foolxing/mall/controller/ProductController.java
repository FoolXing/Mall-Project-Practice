package com.foolxing.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.foolxing.mall.annotation.MyLog;
import com.foolxing.mall.pojo.Product;
import com.foolxing.mall.pojo.VO.ProductVO;
import com.foolxing.mall.pojo.query.ProductQuery;
import com.foolxing.mall.service.IProductService;
import com.foolxing.mall.util.JwtUtil;
import com.foolxing.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
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
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @MyLog(module = "商品模块")
    @GetMapping("/list")
    public Result list(ProductQuery productQuery) {
        IPage<ProductVO> page = productService.list(productQuery);
        return Result.ok(page);
    }

    // /product/deleteById?id=1
    // /product/deleteById/1
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        productService.removeById(id);
        return Result.ok("删除成功");
    }

    // /product/deleteById/1,2,3
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAll(@PathVariable Integer[] ids) {
        productService.removeBatchByIds(Arrays.asList(ids));
        return Result.ok("删除成功");
    }

    // @RequestBody : 封装前台传递过来的JSON格式数据
    // @ResponseBody : 将数据转换为JSON格式返回
    @PostMapping("/add")
    public Result add(@RequestBody Product product) {
        productService.save(product);
        return Result.ok("添加成功");
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Product product = productService.selectById(id);
        return Result.ok(product);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Product product) {
        productService.update(product);
        return Result.ok("更新成功");
    }
    
}

