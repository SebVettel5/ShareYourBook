package com.example.demo.service;

import com.example.demo.domain.Deposits;

public interface DepositsService {
    Deposits getOneByUserId(Long userId);
}
