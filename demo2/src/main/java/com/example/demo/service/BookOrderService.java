package com.example.demo.service;

import com.example.demo.ViewObject.SingleBookOrderWrapper;
import com.example.demo.domain.BookOrders;
import com.example.demo.domain.Deposits;
import com.example.demo.domain.Organization;
import com.example.demo.domain.User;
import com.example.demo.mapper.BookOrderMapper;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.BaseMapper;

@Service
public interface BookOrderService {
    void createBookOrder(SingleBookOrderWrapper s, User u,Long orgId);

    Organization getOrg(Long bookOrderId);

    String updateBackPost(String postName, String postNo,Long bookOrderId);
}
