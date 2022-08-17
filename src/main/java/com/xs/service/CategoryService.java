package com.xs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.R;
import com.xs.entity.Category;

import java.util.List;

/**
 * 菜品及套餐分类(Category)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 07:19:02
 */
public interface CategoryService extends IService<Category> {

    R<Page<Category>> getByPage(Integer page, Integer pageSize);

    R<String> deleteById(Long ids);

    R<Category> getCategoryById(Long id);

    R<String> updateCategory(Category category);

    R<String> addCategory(Category category);

    R<List<Category>> listCategory(Category category);
}

