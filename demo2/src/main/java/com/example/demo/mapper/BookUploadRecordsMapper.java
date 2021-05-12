package com.example.demo.mapper;

import com.example.demo.domain.BookUploadRecords;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface BookUploadRecordsMapper extends BaseMapper<BookUploadRecords> {

    BookUploadRecords selectRecords(Date recordsCreateTime, String recordsBookName, Long orgId);

    int insertRecords(BookUploadRecords bookUploadRecords);

    List<BookUploadRecords> selectOrgUploadRecordsByStatus(Long orgid, String status, Boolean dataStatus);

    int deleteById(Long recordsId,Boolean status,Date date,String optioner);

    BookUploadRecords selectOneById(Long recordsId);

    int updateBookUploadRecords(Long recId, String bookName, String author, String language, String publisherName, String edition, String cip, String cover);

    List<BookUploadRecords> selectAllByStatus(Long orgid, Boolean dataStatus);

    int withdrawById(Long recordsId, String status,Date date,String optioner);

    BookUploadRecords findOne(Long orgId, Long recordsId);
}
