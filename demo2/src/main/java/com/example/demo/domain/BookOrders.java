package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：chenjiajun
 * @description：书籍订单表
 * 书籍订单作为机构订单的子订单，存储着机构订单内含的每一本书籍的借阅信息
 * @date ：2021/5/13 1:23
 */
@Data
@Table(name = "bookorders")
public class BookOrders {
    @Id
    private Long bookOrderId;
    private Long bookOrderOrgOrderId;
    private Date bookOrderCreateTime;
    private Long bookOrderUserId;
    private Long bookOrderOrgId;
    private Long bookOrderBookId;
    private String bookOrderBookName;
    private String bookOrderBookPublisher;
    private String bookOrderBookEdition;
    private String bookOrderBookAuthor;
    private String bookOrderBookPic;
    private Double bookOrderBookPrice;
    private Double bookOrderPostage;
    private Integer bookOrderBookDays;
    private Integer bookOrderBookAmount;
    private Boolean bookOrderDataStatus;
    private Integer bookOrderRemindDays;
    private Date bookOrderUserGetTime;
    private Date bookOrderUserBackTime;
    private String bookOrderBackPostName;
    private Date bookOrderOrgGetTime;
    private String bookOrderBackPostNo;

    public BookOrders(Long bookOrderOrgOrderId, Date bookOrderCreateTime, Long bookOrderUserId, Long bookOrderOrgId, Long bookOrderBookId,
                      String bookOrderBookName, String bookOrderBookPic, Double bookOrderBookPrice, int bookOrderBookDays,
                      int bookOrderBookAmount,Double bookOrderPostage,String bookPublisher, String bookAuthor, String bookEdition) {
        this.bookOrderBookPublisher = bookPublisher;
        System.out.println(bookPublisher+"  "+this.bookOrderBookPublisher);
        this.bookOrderBookAuthor = bookAuthor;
        this.bookOrderBookEdition = bookEdition;
        this.bookOrderOrgOrderId = bookOrderOrgOrderId;
        this.bookOrderCreateTime = bookOrderCreateTime;
        this.bookOrderUserId = bookOrderUserId;
        this.bookOrderOrgId = bookOrderOrgId;
        this.bookOrderBookId = bookOrderBookId;
        this.bookOrderBookName = bookOrderBookName;
        this.bookOrderBookPic = bookOrderBookPic;
        this.bookOrderBookPrice = bookOrderBookPrice;
        this.bookOrderBookDays = bookOrderBookDays;
        this.bookOrderBookAmount = bookOrderBookAmount;
        this.bookOrderPostage = bookOrderPostage;
        this.bookOrderDataStatus =true;
        this.bookOrderRemindDays = bookOrderBookDays;
    }


}
