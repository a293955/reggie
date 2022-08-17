package com.xs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.R;
import com.xs.dto.SetmealDto;
import com.xs.entity.Setmeal;

import java.util.List;

/**
 * 套餐(Setmeal)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 07:20:56
 */
public interface SetmealService extends IService<Setmeal> {

    R<String> saveWithDish(SetmealDto setmealDto);

    R<Page<SetmealDto>> getSetmealByPage(int page, int pageSize, String name);

    R<SetmealDto> getSetmealById(Long id);

    R<String> updateSetmeal(SetmealDto setmealDto);

    R<String> openStatus(Long[] ids);

    R<String> closeStatus(Long[] ids);

    R<String> deleteSetmeal(Long[] ids);

    R<List<Setmeal>> getList(Setmeal setmeal);
}

