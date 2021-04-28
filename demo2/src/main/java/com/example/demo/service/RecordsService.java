package com.example.demo.service;

import com.example.demo.ViewObject.CommentVO;
import com.example.demo.domain.BookUploadRecords;
import com.example.demo.domain.BorrowForms;
import com.example.demo.domain.Collection;
import com.example.demo.domain.Organization;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;

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

    int updateUploadRecords(BookUploadRecords bookUploadRecords);

    PageInfo<BookUploadRecords> getOrgUploadRecordsByStatus(Long orgid, String status, int pageNum, int pageSize,Boolean dataStatus) throws ParseException;

    String changeUploadRecordsById(String optionName, String[] arr);

    String getBookUploadsByid(Model model,Long recordsId);

    String updateRecords(Model model,Long recId, String bookName, String author, String language, String publisherName, String edition, String cip, String cover);

    PageInfo<BookUploadRecords> getAllUploadRecordPages(Long orgId, int pageNum,int pageSize);

    BookUploadRecords getBookUploadsById(Long recordsId);
//
//    BookUploadRecords getOneUploadRecord(Long orgId, Long recordsId);
}
