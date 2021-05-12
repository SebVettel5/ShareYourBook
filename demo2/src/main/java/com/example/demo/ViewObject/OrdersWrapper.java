package com.example.demo.ViewObject;

import com.example.demo.domain.Book;
import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ：chenjiajun
 * @description：交易订单包装类
 * 主要是将一次订单付款的信息包装起来传输
 * @date ：2021/5/10 22:47
 */
@Data
public class OrdersWrapper {
    private Set<Long> bookIdSet;
    private HashMap<SingleOrgOrderWrapper,SingleBookOrderWrapper> bookAmount;
    private Long userId;
//    private HashMap<Book,Integer> bookMap;

    public OrdersWrapper() {
        this.bookIdSet = new HashSet<>();
        this.bookAmount = new HashMap<>();
    }
}
