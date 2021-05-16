package com.example.demo.service.Impl;

import com.example.demo.domain.Deposits;
import com.example.demo.mapper.DepositsMapper;
import com.example.demo.service.DepositsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：chenjiajun
 * @description：用户押金服务类
 * @date ：2021/5/14 16:51
 */
@Service
public class DepositsServiceImpl implements DepositsService {
    @Autowired
    private DepositsMapper depositsMapper;

    @Override
    public Deposits getOneByUserId(Long userId) {
        return depositsMapper.getOneByUserId(userId);
    }
}
