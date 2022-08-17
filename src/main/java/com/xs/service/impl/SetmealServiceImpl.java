package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.R;
import com.xs.dao.CategoryDao;
import com.xs.dao.SetmealDao;
import com.xs.dao.SetmealDishDao;
import com.xs.dto.SetmealDto;
import com.xs.entity.Category;
import com.xs.entity.Setmeal;
import com.xs.entity.SetmealDish;
import com.xs.service.SetmealDishService;
import com.xs.service.SetmealService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 套餐(Setmeal)表服务实现类
 *
 * @author makejava
 * @since 2022-08-11 07:20:56
 */
@Service("setmealService")
public class SetmealServiceImpl extends ServiceImpl<SetmealDao, Setmeal> implements SetmealService {

    @Resource
    private SetmealDao setmealDao;

    @Resource
    private SetmealDishDao setmealDishDao;

    @Resource
    private SetmealDishService setmealDishService;

    @Resource
    private CategoryDao categoryDao;

    @Override
    @Transactional
    public R<String> saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal，执行insert操作
        setmealDao.insert(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        List<SetmealDish> list = setmealDishes.stream().peek((item) -> item.setSetmealId(setmealDto.getId())).toList();

        setmealDishService.saveBatch(list);

        return R.success("新增套餐成功");
    }

    @Override
    public R<Page<SetmealDto>> getSetmealByPage(int page, int pageSize, String name) {
        //分页构造器对象
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        lqw.like(Strings.isNotEmpty(name), Setmeal::getName, name);
        //添加排序条件，根据更新时间降序排列
        lqw.orderByDesc(Setmeal::getUpdateTime);

        setmealDao.selectPage(setmealPage, lqw);

        //对象拷贝
        BeanUtils.copyProperties(setmealPage, setmealDtoPage,"records");
        List<Setmeal> records = setmealPage.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item, setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryDao.selectById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).toList();
        setmealDtoPage.setRecords(list);

        return R.success(setmealDtoPage);
    }

    @Override
    public R<SetmealDto> getSetmealById(Long id) {
        Setmeal setmeal = setmealDao.selectById(id);

        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> flavors = setmealDishDao.selectList(lqw);
        setmealDto.setSetmealDishes(flavors);

        return R.success(setmealDto);
    }

    @Override
    public R<String> updateSetmeal(SetmealDto setmealDto) {
        setmealDao.updateById(setmealDto);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishDao.delete(lqw);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        List<SetmealDish> list = setmealDishes.stream().peek((item -> item.setSetmealId(setmealDto.getId()))).toList();
        setmealDishService.saveBatch(list);

        return R.success("修改套餐成功");
    }

    @Override
    public R<String> openStatus(Long[] ids) {
        for (Long id : ids) {
            Setmeal setmeal = setmealDao.selectById(id);
            setmeal.setStatus(1);
            setmealDao.updateById(setmeal);
        }
        return R.success("套餐启用成功");
    }

    @Override
    public R<String> closeStatus(Long[] ids) {
        for (Long id : ids) {
            Setmeal setmeal = setmealDao.selectById(id);
            setmeal.setStatus(0);
            setmealDao.updateById(setmeal);
        }
        return R.success("套餐禁用成功");
    }

    @Override
    public R<String> deleteSetmeal(Long[] ids) {
        for (Long id : ids) {
            setmealDao.deleteById(id);
        }
        return R.success("套餐删除成功");
    }

    @Override
    public R<List<Setmeal>> getList(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Objects.nonNull(setmeal.getCategoryId()), Setmeal::getCategoryId, setmeal.getCategoryId())
                .eq(Setmeal::getStatus, 1)
                .orderByDesc(Setmeal::getCreateTime);
        List<Setmeal> setmeals = setmealDao.selectList(lqw);

        return R.success(setmeals);
    }
}

