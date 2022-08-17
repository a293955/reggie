package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.R;
import com.xs.dao.EmployeeDao;
import com.xs.entity.Employee;
import com.xs.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 员工信息(Employee)表服务实现类
 *
 * @author makejava
 * @since 2022-08-11 07:20:15
 */
@Service("employeeService")
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
    @Resource
    private EmployeeDao employeeDao;

    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        // ①. 将页面提交的密码password进行md5加密处理, 得到加密后的字符串
        String password = DigestUtils.md5DigestAsHex(employee.getPassword().getBytes());
        //②. 根据页面提交的用户名username查询数据库中员工数据信息
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeDao.selectOne(lqw);
        //③. 如果没有查询到, 则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }
        //④. 密码比对，如果不一致, 则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }
        //⑤. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        //⑥. 登录成功，将员工id存入Session, 并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @Override
    public R<String> logout(HttpServletRequest request) {
        //A. 清理Session中的用户id
        request.getSession().removeAttribute("employee");
        //B. 返回结果
        return R.success("退出成功");
    }

    @Override
    public R<String> saveEmployee(Employee employee) {
        //设置初始密码，用md5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        employeeDao.insert(employee);
        return R.success("新增员工成功");
    }

    @Override
    public R<Page<Employee>> getByPage(Integer page, Integer pageSize, String name) {
        //A. 构造分页条件
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        //B. 构建搜索条件 - name进行模糊匹配
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null, Employee::getName, name);
        //C. 构建排序条件 - 更新时间倒序排序
        lqw.orderByDesc(Employee::getUpdateTime);
        //D. 执行查询
        employeeDao.selectPage(pageInfo, lqw);
        //E. 组装结果并返回
        return R.success(pageInfo);
    }

    @Override
    public R<String> updateEmployee(Employee employee) {
        employeeDao.updateById(employee);
        return R.success("员工消息修改成功");
    }

    @Override
    public R<Employee> getEmplyeeById(Long id) {
        Employee employee = employeeDao.selectById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有该id对应的员工！");
    }


}

