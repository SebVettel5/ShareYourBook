package com.example.demo.ViewObject;

import com.example.demo.domain.Book;
import com.example.demo.domain.BookOrders;
import com.example.demo.domain.Cart;
import lombok.Data;

/**
 * @author ：chenjiajun
 * @description：单条图书借阅订单包装类
 * @date ：2021/5/12 17:30
 */
@Data
public class SingleBookOrderWrapper {
    private Long bookId;
    private String bookPic;
    private String bookName;
    private String bookPublisher;
    private String bookEdition;
    private String bookAuthor;
    private Double bookPrice;
    private int bookAmount;
    private int bookDays;
    private Double bookPostage;
    private Double curSubTotal;
    private Long orgOrderId;
    //存放书籍基本信息
    private Book book;
    private String bookAmountDaysInfo;

    public SingleBookOrderWrapper(Long bookId, int bookAmount, int bookDays) {
        this.bookId = bookId;
        this.bookAmount = bookAmount;
        this.bookDays = bookDays;
    }

    public SingleBookOrderWrapper(Cart c) {
        this.bookId = c.getCartBookId();
        this.bookAmount = c.getCartBookAmount();
        this.bookDays = c.getCartBookDays();
    }


    /**
    * @Description: 对查询到的图书信息进行处理
    * @Param: [book]
    * @return: void
    * @Author: chenjiajun
    * @Date: 2021/5/12
    */
    public void preValue(Book book) {
        this.bookPublisher =book.getBookPublisher();
        this.bookAuthor = book.getBookAuthor();
        this.bookEdition = book.getBookEdition();
        this.bookPic = book.getBookCover();
        this.bookName = book.getBookName();
        this.bookPrice = book.getBookPrice();
        if (this.bookDays < 30){
            this.bookPostage = 8.00;
        }else{
            this.bookPostage = 0.00;
        }
        this.curSubTotal = this.bookPrice * this.bookAmount*this.bookDays+this.bookPostage;
    }

    public SingleBookOrderWrapper(Double curSubTotal) {
        this.curSubTotal = curSubTotal;
    }

    /**
    * @Description: 更新封装类信息
    * @Param: [days]
    * @return: java.lang.Double
    * @Author: chenjiajun
    * @Date: 2021/5/13
    */
    public void bookUpdate(int days,Long orgOderId) {
        this.setBookDays(days);
        if (days>30){
            bookPostage = 0.0;
            curSubTotal = 0.0;
        }
        curSubTotal = bookPostage + (bookDays*bookPrice);
        this.setOrgOrderId(orgOderId);
    }

    @Override
    public String toString() {
        return "SingleBookOrderWrapper{" +
                "bookId=" + bookId +
                ", bookPic='" + bookPic + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookAmount=" + bookAmount +
                ", bookDays=" + bookDays +
                ", bookPostage=" + bookPostage +
                ", curSubTotal=" + curSubTotal +
                ", orgOrderId=" + orgOrderId +
                '}';
    }
}
