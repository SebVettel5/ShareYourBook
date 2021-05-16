package com.example.demo.mapper;

import com.example.demo.domain.BookOrders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface BookOrderMapper extends BaseMapper<BookOrders> {
    void createBookOrderRecords(Long orgOrderId, Date date, Long userId, Long orgId, Long bookId, String bookName, String bookPic, Double bookPrice, int bookDays);

    List<BookOrders> getAllByIdAndStatus(Long userId, Long orderNo, boolean dataStatus);

    void getConfirmOrders(List<Long> list, Long userId, Date date);

    int updateBackInfo(String postName, String postNo,Long bookOrderId,Date date);
}
