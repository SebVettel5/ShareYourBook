package com.example.demo.service;

import com.example.demo.domain.Book;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    PageInfo<Book> getBooksByIdAndStatus(Long orgId, String status, Boolean dataStatus, int pageNum);

    String changeStatus(Long orgId, String[] arr);

    String saveChanges(Long orgId, String[] arr);

    PageInfo<Book> selectAllByOrgId(Long orgId, int pageNum);

    String delete(Long orgId, Long bookId);

    PageInfo<Book> getAllOnSaleBook(int pageNum);

    Book getBook(Long bookId);

    PageInfo<Book> fuzzySearch(String searchString, int pageNum,Long orgId);

    Book getOderBook(Long bookId);
}
