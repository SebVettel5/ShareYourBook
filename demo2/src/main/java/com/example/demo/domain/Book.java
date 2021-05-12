package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：书籍实体类
 * @date ：2021/4/12 21:55
 */
@Data
@Table(name = "books")
public class Book {

    @Id
    private Long bookId;
    private Long bookOrgId;
    private String bookCipId;
    private String bookCover;
    private String bookEdition;
    private String bookPublisher;
    private String bookLanguage;
    private String bookAuthor;
    private String bookName;
    private String bookStatus;
    private Double bookPrice;
    private Long bookAmount;
    private String bookDescription;
    private Boolean bookDataStatus;
    private String bookOrgName;

    public Book(String bookCover, String bookEdition, String bookPublisher, String bookLanguage, String bookAuthor, String bookName, String bookStatus, Double bookPrice, Long bookAmount, String bookDescription) {
        this.bookCover = bookCover;
        this.bookEdition = bookEdition;
        this.bookPublisher = bookPublisher;
        this.bookLanguage = bookLanguage;
        this.bookAuthor = bookAuthor;
        this.bookName = bookName;
        this.bookStatus = bookStatus;
        this.bookPrice = bookPrice;
        this.bookAmount = bookAmount;
        this.bookDescription = bookDescription;
    }

    public Book(Long bookId, Double bookPrice, Long bookAmount, String bookDescription) {
        this.bookId = bookId;
        this.bookPrice = bookPrice;
        this.bookAmount = bookAmount;
        this.bookDescription = bookDescription;
    }

    public Book(Long bookId, Long bookOrgId, String bookCover, String bookName, Double bookPrice, String bookOrgName) {
        this.bookId = bookId;
        this.bookOrgId = bookOrgId;
        this.bookCover = bookCover;
        this.bookName = bookName;
        this.bookPrice = bookPrice;
        this.bookOrgName = bookOrgName;
    }
}
