package com.xs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xs.common.R;
import com.xs.dto.DishDto;
import com.xs.entity.Dish;
import com.xs.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private DishService dishService;

    /**
     * 批量启售
     */
    @PostMapping("/status/1")
    public R<String> openStatus(Long[] ids) {
        return dishService.openStatus(ids);
    }

    /**
     * 批量停售
     */
    @PostMapping("/status/0")
    public R<String> updateStatus(Long[] ids) {
        return dishService.updateStatus(ids);
    }

    /**
     * 批量删除
     */
    @DeleteMapping
    public R<String> deleteDish(Long[] ids) {
        return dishService.deleteDish(ids);
    }

    /**
     * 添加菜品
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        return dishService.addDish(dishDto);
    }

    /**
     * 分页查询菜品
     */
    @GetMapping("/page")
    public R<Page<DishDto>> getByPage(Integer page, Integer pageSize, String name) {
        return dishService.getByPage(page, pageSize, name);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     */
    @GetMapping("/{id}")
    public R<DishDto> getByIdWithFlavor(@PathVariable Long id){
        return dishService.getByIdWithFlavor(id);
    }

    /**
     * 查询菜品数据
     */
    @GetMapping("/list")
    public R<List<DishDto>> listDish(Dish dish) {
        return dishService.listDish(dish);
    }

    /**
     * 修改菜品
     */
    @PutMapping
    public R<String> updateWithFlavor(@RequestBody DishDto dishDto){
        //log.info(dishDto.toString());
        return dishService.updateWithFlavor(dishDto);
    }
}
