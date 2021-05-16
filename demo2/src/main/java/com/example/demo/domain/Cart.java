package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：购物车实体类
 * @date ：2021/5/16 15:00
 */
@Data
@Table(name = "cart")
public class Cart {
    @Id
    private Long cartId;
    private Long cartBookOrgId;
    private Long cartUserId;
    private Integer cartBookAmount;
    private Integer cartBookDays;
    private Long cartBookId;
    private Double cartPostage;

    public Cart(String bookId, Long userId, Long bookOrgId, String booksAmount, String booksDays) {
        this.cartBookId = new Long(bookId);
        this.cartUserId = userId;
        this.cartBookAmount = Integer.valueOf(booksAmount);
        this.cartBookOrgId = bookOrgId;
        this.cartBookDays = Integer.valueOf(booksDays);
        if (this.getCartBookDays()>30){
            this.setCartPostage(0.00);
        }else{
            this.setCartPostage(8.00);
        }
    }

    public Cart(Cart cart) {
        this.cartBookId = cart.getCartBookId();
        this.cartUserId = cart.getCartUserId();
        this.cartBookOrgId = cart.getCartBookOrgId();
    }

    public Cart(Long userId) {
        this.cartUserId =userId;
    }



    public Cart(Long cartId, Integer cartBookAmount, Integer cartBookDays, Double cartPostage) {
        this.cartId = cartId;
        this.cartBookAmount = cartBookAmount;
        this.cartBookDays = cartBookDays;
        this.cartPostage = cartPostage;
    }
}
