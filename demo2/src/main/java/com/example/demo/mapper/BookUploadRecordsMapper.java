package com.example.demo.mapper;

import com.example.demo.domain.BookUploadRecords;
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
    List<BookUploadRecords> selectOrgUploadRecordsByStatus(Long orgid,String status);
}
