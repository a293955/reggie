package com.xs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.R;
import com.xs.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**
 * 员工信息(Employee)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 07:20:15
 */
public interface EmployeeService extends IService<Employee> {

    R<Employee> login(HttpServletRequest request, Employee employee);

    R<String> logout(HttpServletRequest request);

    R<String> saveEmployee(Employee employee);

    R<Page<Employee>> getByPage(Integer page, Integer pageSize, String name);

    R<String> updateEmployee(Employee employee);

    R<Employee> getEmplyeeById(Long id);
}

