package com.example.demo.ViewObject;

import lombok.Data;

/**
 * @author ：chenjiajun
 * @description：单条图书借阅订单包装类
 * @date ：2021/5/12 17:30
 */
@Data
public class SingleBookOrderWrapper {
    private String bookPic;
    private String BookName;
    private Double bookPrice;
    private int bookAmount;
    private int bookDays;
    private Double bookPostage;
    private Double curSubTotal;
}
