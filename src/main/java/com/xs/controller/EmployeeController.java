package com.xs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xs.common.R;
import com.xs.entity.Employee;
import com.xs.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 员工信息(Employee)表控制层
 *
 * @author makejava
 * @since 2022-08-11 07:43:25
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    /**
     * 服务对象
     */
    @Resource
    private EmployeeService employeeService;

    /**
     * 通过id查询员工信息
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id) {
        log.info(String.valueOf(id));
        return employeeService.getEmplyeeById(id);
    }
    /**
     * 禁用或修改账户
     */
    @PutMapping
    public R<String> updateEmployee(@RequestBody Employee employee) {
        log.info(employee.toString());
        return employeeService.updateEmployee(employee);
    }
    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<Page<Employee>> getByPage(Integer page, Integer pageSize, String name) {
        log.info("page：{}, pageSize：{}, name：{}", page, pageSize, name);
        return employeeService.getByPage(page, pageSize, name);
    }
    /**
     * 添加员工
     */
    @PostMapping
    public R<String> saveEmployee(@RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());
        return employeeService.saveEmployee(employee);
    }
    /**
     * 登录功能
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.login(request, employee);
    }

    /**
     * 登出功能
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        return employeeService.logout(request);
    }
}

