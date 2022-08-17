package com.xs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xs.common.R;
import com.xs.dto.SetmealDto;
import com.xs.entity.Setmeal;
import com.xs.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    /**
     * 套餐按分类查询
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<Setmeal>> getList(Setmeal setmeal) {
        return setmealService.getList(setmeal);
    }

    /**
     * 启用套餐
     */
    @PostMapping("/status/1")
    public R<String> openStatus(Long[] ids) {
        return setmealService.openStatus(ids);
    }

    /**
     * 禁用套餐
     */
    @PostMapping("/status/0")
    public R<String> closeStatus(Long[] ids) {
        return setmealService.closeStatus(ids);
    }

    /**
     * 删除套餐
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true) //清除setmealCache名称下,所有的缓存数据
    public R<String> deleteSetmeal(Long[] ids) {
        return setmealService.deleteSetmeal(ids);
    }

    /**
     * 修改套餐
     */
    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDto setmealDto) {
        return setmealService.updateSetmeal(setmealDto);
    }

    /**
     * 按id查询套餐信息
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealById(@PathVariable Long id) {
        return setmealService.getSetmealById(id);
    }

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true) //清除setmealCache名称下,所有的缓存数据
    public R<String> saveWithDish(@RequestBody SetmealDto setmealDto) {
        return setmealService.saveWithDish(setmealDto);
    }

    /**
     * 套餐分页查询
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> getSetmealByPage(int page, int pageSize, String name){
        return setmealService.getSetmealByPage(page, pageSize, name);
    }
}
