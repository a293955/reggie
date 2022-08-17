package com.xs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xs.common.R;
import com.xs.entity.Category;
import com.xs.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 根据条件查询分类数据
     */
    @GetMapping("/list")
    public R<List<Category>> listCategory(Category category){
        return categoryService.listCategory(category);
    }

    /**
     * 分页查询菜品
     */
    @GetMapping("/page")
    public R<Page<Category>> getByPage(Integer page, Integer pageSize) {
        return categoryService.getByPage(page, pageSize);
    }

    /**
     *删除菜品
     */
    @DeleteMapping
    public R<String> deleteById(Long ids) {
        return categoryService.deleteById(ids);
    }

    /**
     * 通过id查询菜品
     */
    @GetMapping("/{id}")
    public R<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * 修改菜品
     */
    @PutMapping
    public R<String> updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    /**
     * 添加菜品分类
     */
    @PostMapping
    public R<String> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
}
