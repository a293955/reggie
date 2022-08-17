package com.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.common.R;
import com.xs.entity.AddressBook;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 地址管理(AddressBook)表服务接口
 *
 * @author makejava
 * @since 2022-08-11 07:16:13
 */
public interface AddressBookService extends IService<AddressBook> {

    R<List<AddressBook>> getList();

    R<AddressBook> getAddressBookById(Long id);

    R<AddressBook> addAddressBook(AddressBook addressBook);

    R<String> updateAddressBook(AddressBook addressBook);

    R<AddressBook> SetDefaultAddressBook(AddressBook addressBook);

    R<AddressBook> getDefault();

    R<String> deleteAddressBook(Long ids);
}

