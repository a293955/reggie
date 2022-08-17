package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.BaseContext;
import com.xs.common.R;
import com.xs.dao.AddressBookDao;
import com.xs.entity.AddressBook;
import com.xs.service.AddressBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

/**
 * 地址管理(AddressBook)表服务实现类
 *
 * @author makejava
 * @since 2022-08-11 07:16:13
 */
@Service("addressBookService")
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBook> implements AddressBookService {

    @Resource
    private AddressBookDao addressBookDao;

    @Override
    public R<List<AddressBook>> getList() {
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getIsDeleted, 0)
                .eq(AddressBook::getUserId, BaseContext.getCurrentId())
                .orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> addressBooks = addressBookDao.selectList(lqw);

        return R.success(addressBooks);
    }

    @Override
    public R<AddressBook> getAddressBookById(Long id) {
        AddressBook addressBook = addressBookDao.selectById(id);
        if (Objects.nonNull(addressBook)) {
            return R.success(addressBook);
        }
        return R.error("没有查询到该id对应的地址");
    }

    @Override
    public R<AddressBook> addAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookDao.insert(addressBook);
        return R.success(addressBook);
    }

    @Override
    public R<String> updateAddressBook(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookDao.updateById(addressBook);
        return R.success("地址修改成功");
    }

    @Override
    public R<AddressBook> SetDefaultAddressBook(AddressBook addressBook) {
        LambdaUpdateWrapper<AddressBook> lqw = new LambdaUpdateWrapper<>();
        lqw.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        lqw.set(AddressBook::getIsDefault, 0);
        addressBookDao.update(addressBook, lqw);

        addressBook.setIsDefault(1);
        addressBookDao.updateById(addressBook);

        return R.success(addressBook);
    }

    @Override
    public R<AddressBook> getDefault() {
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        lqw.eq(AddressBook::getIsDefault, 1);

        AddressBook addressBook = addressBookDao.selectOne(lqw);

        if (Objects.isNull(addressBook)) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBook);
        }
    }

    @Override
    public R<String> deleteAddressBook(Long ids) {
        addressBookDao.deleteById(ids);
        return R.success("地址删除成功");
    }
}

