package com.example.demo.service;

import com.example.demo.ViewObject.CartWrapper;
import com.example.demo.ViewObject.SingleBookOrderWrapper;
import com.example.demo.domain.Cart;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CartService {
    int insert(Cart cart);

    PageInfo<CartWrapper> getUserCart(Long userId, int pageNum);

    String removeCarts(String[] arr,Long userId);

    void updateCart(Long userId, Cart cartTemp);

    List<SingleBookOrderWrapper> getUserCartList(Long userId, String[] arr, List<SingleBookOrderWrapper> list);
}
