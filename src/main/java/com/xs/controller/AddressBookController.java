package com.xs.controller;

import com.xs.common.R;
import com.xs.entity.AddressBook;
import com.xs.service.AddressBookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Resource
    private AddressBookService addressBookService;

    /**
     * 删除地址
     */
    @DeleteMapping
    public R<String> deleteAddressBook(Long ids) {
        return addressBookService.deleteAddressBook(ids);
    }
    
    /**
     * 查询默认地址
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault() {
        return addressBookService.getDefault();
    }

    /**
     * 设置默认地址
     */
    @PutMapping("/default")
    private R<AddressBook> SetDefaultAddressBook(@RequestBody AddressBook addressBook) {
        return addressBookService.SetDefaultAddressBook(addressBook);
    }

    /**
     * 地址修改
     */
    @PutMapping
    public R<String> updateAddressBook(@RequestBody AddressBook addressBook) {
        return addressBookService.updateAddressBook(addressBook);
    }

    /**
     * 地址新增
     */
    @PostMapping
    public R<AddressBook> addAddressBook(@RequestBody AddressBook addressBook) {
        return addressBookService.addAddressBook(addressBook);
    }

    /**
     * 查询所有地址信息
     */
    @GetMapping("/list")
    public R<List<AddressBook>> getList() {
        return addressBookService.getList();
    }

    /**
     * 按id查询地址信息
     */
    @GetMapping("/{id}")
    public R<AddressBook> getAddressBookById(@PathVariable Long id) {
        return addressBookService.getAddressBookById(id);
    }
}
