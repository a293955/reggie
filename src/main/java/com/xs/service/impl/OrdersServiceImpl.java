package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.BaseContext;
import com.xs.common.R;
import com.xs.dao.*;
import com.xs.dto.OrdersDto;
import com.xs.entity.*;
import com.xs.exception.CustomException;
import com.xs.service.OrderDetailService;
import com.xs.service.OrdersService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 订单表(Orders)表服务实现类
 *
 * @author makejava
 * @since 2022-08-11 07:20:42
 */
@Service("ordersService")
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Resource
    private OrdersDao ordersDao;

    @Resource
    private OrderDetailService orderDetailService;

    @Resource
    private OrderDetailDao orderDetailDao;

    @Resource
    private ShoppingCartDao shoppingCartDao;

    @Resource
    private UserDao userDao;

    @Resource
    private AddressBookDao addressBookDao;

    @Override
    public R<Page<Orders>> getOrdersByPage(Integer page, Integer pageSize, Long number, LocalDateTime beginTime, LocalDateTime endTime) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.like(Objects.nonNull(number), Orders::getNumber, number)
                .gt(Objects.nonNull(beginTime), Orders::getOrderTime,beginTime)
                .lt(Objects.nonNull(endTime), Orders::getOrderTime,endTime);
        ordersDao.selectPage(ordersPage, lqw);

        return R.success(ordersPage);
    }

    @Override
    public R<Page<OrdersDto>> getUserPage(Integer page, Integer pageSize) {
        //分页构造器对象
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        Page<OrdersDto> pageDto = new Page<>(page, pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        //这里直接把分页的全部结果查询出来，没有分页条件
        //添加排序条件，根据更新时间降序排列
        lqw.eq(Orders::getUserId, BaseContext.getCurrentId())
                .orderByDesc(Orders::getOrderTime);
        ordersDao.selectPage(pageInfo, lqw);

        //对OrderDto进行需要的属性赋值
        List<Orders> records = pageInfo.getRecords();
        List<OrdersDto> orderDtoList = records.stream().map((item) ->{
            OrdersDto orderDto = new OrdersDto();
            //此时的orderDto对象里面orderDetails属性还是空 下面准备为它赋值
            Long orderId = item.getId();//获取订单id
            LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
            BeanUtils.copyProperties(item, orderDto);
            //对orderDto进行OrderDetails属性的赋值
            orderDto.setOrderDetails(orderDetailList);
            return orderDto;
        }).collect(Collectors.toList());

        //使用dto的分页有点难度.....需要重点掌握
        BeanUtils.copyProperties(pageInfo, pageDto,"records");
        pageDto.setRecords(orderDtoList);
        return R.success(pageDto);
    }

    @Override
    public R<String> submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();

        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartDao.selectList(lqw);

        if(shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }

        //查询用户数据
        User user = userDao.selectById(userId);

        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookDao.selectById(addressBookId);
        if(addressBook == null){
            throw new CustomException("用户地址信息有误，不能下单");
        }

        long orderId = IdWorker.getId();//订单号

        AtomicInteger amount = new AtomicInteger(0);

        //组装订单明细信息
        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet((int) ((item.getAmount()) * (item.getNumber())));
            return orderDetail;
        }).collect(Collectors.toList());

        //组装订单数据
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(amount.get());//总金额
        orders.setUserId(userId);
        orders.setNumber(orderId);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车数据
        shoppingCartDao.delete(lqw);

        return R.success("下单成功");
    }

    @Override
    public R<Orders> updateStatus(Orders orders) {
        if (Objects.nonNull(orders.getStatus()) && orders.getStatus() == 3) {
            orders.setStatus(3);
        }
        if (Objects.nonNull(orders.getStatus()) && orders.getStatus() == 4) {
            orders.setStatus(4);
        }
        ordersDao.updateById(orders);
        return R.success(orders);
    }

    @Override
    public R<String> again(Orders orders) {
        ShoppingCart shoppingCart = new ShoppingCart();
        Orders order = ordersDao.selectById(orders.getId());
        LambdaQueryWrapper<OrderDetail> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Objects.nonNull(orders.getId()), OrderDetail::getOrderId, orders.getId());
        List<OrderDetail> orderDetails = orderDetailDao.selectList(lqw);
        for (OrderDetail orderDetail : orderDetails) {
            shoppingCart.setId(orderDetail.getId() + 999);
            shoppingCart.setName(orderDetail.getName());
            shoppingCart.setImage(orderDetail.getImage());
            shoppingCart.setUserId(order.getUserId());
            if (Objects.nonNull(orderDetail.getDishId())) {
                shoppingCart.setDishId(orderDetail.getDishId());
            }
            if (Objects.nonNull(orderDetail.getSetmealId())) {
                shoppingCart.setSetmealId(orderDetail.getSetmealId());
            }
            shoppingCart.setDishFlavor(orderDetail.getDishFlavor());
            shoppingCart.setNumber(orderDetail.getNumber());
            shoppingCart.setAmount(orderDetail.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartDao.insert(shoppingCart);
        }
        return R.success("再来一单添加成功");
    }

}

