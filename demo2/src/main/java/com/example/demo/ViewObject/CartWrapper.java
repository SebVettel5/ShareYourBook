package com.example.demo.ViewObject;

import com.example.demo.domain.Book;
import com.example.demo.domain.Cart;
import lombok.Data;

/**
 * @author ：chenjiajun
 * @description：购物车视图包装类
 * @date ：2021/5/16 15:52
 */
@Data
public class CartWrapper {
    private Cart cartItem;
    private Book book;
}
