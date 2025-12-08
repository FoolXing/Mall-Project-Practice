package com.foolxing.mall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.foolxing.mall.pojo.Category;
import com.foolxing.mall.pojo.query.CategoryQuery;
import com.foolxing.mall.service.ICategoryService;
import com.foolxing.mall.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    private Map<String,Object> map = new HashMap<>();

    @GetMapping("/list")
    public Result list(CategoryQuery categoryQuery) {
        IPage<Category> page = (IPage<Category>) map.get("page");
        if (ObjectUtils.isEmpty(page)) {
            page = categoryService.list(categoryQuery);
            map.put("page",page);
        }
        return Result.ok(page);
    }



    // /category/deleteById?id=1
    // /category/deleteById/1
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        categoryService.removeById(id);
        return Result.ok("删除成功");
    }

    // /category/deleteById/1,2,3
    @DeleteMapping("/deleteAll/{ids}")
    public Result deleteAll(@PathVariable Integer[] ids) {
        categoryService.removeBatchByIds(Arrays.asList(ids));
        return Result.ok("删除成功");
    }

    // @RequestBody : 封装前台传递过来的JSON格式数据
    // @ResponseBody : 将数据转换为JSON格式返回
    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        categoryService.save(category);
        return Result.ok("添加成功");
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Category category = categoryService.getById(id);
        return Result.ok(category);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.ok("更新成功");
    }

    @GetMapping("/selectTopCategoryList")
    public Result<List<Category>> selectTopCategoryList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",0);
        List<Category> list = categoryService.list(queryWrapper);
        return Result.ok(list);
    }

    @GetMapping("/selectSecondCategoryListByParentId/{parentId}")
    public Result<List<Category>> selectSecondCategoryListByParentId(@PathVariable Integer parentId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",parentId);
        List<Category> list = categoryService.list(queryWrapper);
        return Result.ok(list);
    }
}

