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
    private Long bookPrice;
    private Long bookAmount;
    private String bookDescription;

}
