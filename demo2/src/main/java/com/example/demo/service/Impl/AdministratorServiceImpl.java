package com.example.demo.service.Impl;

import com.example.demo.domain.Administrator;
import com.example.demo.mapper.AdministratorMapper;
import com.example.demo.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：管理员服务实现类
 * @date ：2021/2/3 14:11
 */
@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {
    @Resource
    private AdministratorMapper administratorMapper;

    @Override
    public List<Administrator> SellectAll() {
        return administratorMapper.selectAll();
    }
}
