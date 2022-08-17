package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.R;
import com.xs.dao.CategoryDao;
import com.xs.entity.Category;
import com.xs.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜品及套餐分类(Category)表服务实现类
 *
 * @author makejava
 * @since 2022-08-11 07:19:02
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Resource
    private CategoryDao categoryDao;

    /**
     * 菜品分页查询
     */
    @Override
    public R<Page<Category>> getByPage(Integer page, Integer pageSize) {
        Page<Category> categoryPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        lqw.orderByAsc(Category::getSort);
        categoryDao.selectPage(categoryPage, lqw);
        return R.success(categoryPage);
    }

    @Override
    public R<String> deleteById(Long ids) {
        categoryDao.deleteById(ids);
        return R.success("菜品删除成功");
    }

    @Override
    public R<Category> getCategoryById(Long id) {
        Category category = categoryDao.selectById(id);
        if (category != null) {
            return R.success(category);
        }
        return R.error("没有该id对应的菜品");
    }

    @Override
    public R<String> updateCategory(Category category) {
        categoryDao.updateById(category);
        return R.success("菜品修改成功");
    }

    @Override
    public R<String> addCategory(Category category) {
        categoryDao.insert(category);
        return R.success("菜品分类添加成功");
    }

    @Override
    public R<List<Category>> listCategory(Category category) {
        //条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //添加条件
        lqw.eq(category.getType() != null, Category::getType, category.getType());
        //添加排序条件
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryDao.selectList(lqw);
        return R.success(list);
    }
}

