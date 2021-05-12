package com.example.demo.service.Impl;

import com.example.demo.domain.Address;
import com.example.demo.mapper.AddressMapper;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：chenjiajun
 * @description：地址服务实现类
 * @date ：2021/5/6 22:54
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> getAllAddressByUser(Long userId) {
        return addressMapper.getAllByUserId(userId);
    }
}
