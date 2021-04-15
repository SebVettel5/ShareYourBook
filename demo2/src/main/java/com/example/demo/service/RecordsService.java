package com.example.demo.service;

import com.example.demo.ViewObject.CommentVO;
import com.example.demo.domain.BookUploadRecords;
import com.example.demo.domain.BorrowForms;
import com.example.demo.domain.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;


public interface RecordsService {
    //Collections
    CommentVO<Collection> getAllCollections();

//    CommentVO<Collection> getCollectionsByUserId(HttpServletRequest request);

    CommentVO<Collection> getUserCollection(Integer page,Integer limit,HttpServletRequest request);

    CommentVO<BorrowForms> getAllBorrowForms();

    BookUploadRecords uploadRecords(BookUploadRecords bookUploadRecords);

    void updateUploadRecords(BookUploadRecords bookUploadRecords);

    List<BookUploadRecords> getOrgUploadRecordsByStatus(Long orgid,String status) throws ParseException;
}
