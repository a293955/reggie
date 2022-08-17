package com.xs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.R;
import com.xs.dto.DishDto;
import com.xs.entity.Dish;

import java.util.List;

/**
 * 菜品管理(Dish)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 07:19:19
 */
public interface DishService extends IService<Dish> {

    R<Page<DishDto>> getByPage(Integer page, Integer pageSize, String name);

    R<DishDto> getByIdWithFlavor(Long id);

    R<String> updateWithFlavor(DishDto dishDto);

    R<String> addDish(DishDto dishDto);

    R<String> deleteDish(Long[] ids);

    R<String> updateStatus(Long[] ids);

    R<String> openStatus(Long[] ids);

    R<List<DishDto>> listDish(Dish dish);
}

