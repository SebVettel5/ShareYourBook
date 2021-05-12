package com.example.demo.mapper;

import com.example.demo.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Component
public interface BooksMapper extends BaseMapper<Book> {
    List<Book> getBookByStatus(Long orgId,String status,Boolean dataStatus);

    int changeStatusById(Long bookId, Long orgId, String status);

    Book checkLegality(Long orgId,Long bookId);

    List<Book> selectAllByOrgId(Long orgId);

    void statusTrigger(Long bookId);

    int deleteById(Long orgId, Long bookId);

    List<Book> selectAllByStatus(String status);

    Book getBookById(Long bookId);

    List<Book> fuzzySearchBooks(String searchString,String bookStatus,Boolean dataStatus,String orgId);

    Book orderBookInfo(Long bookId);
}
