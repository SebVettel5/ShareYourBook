package com.example.demo.service.Impl;

import com.example.demo.ViewObject.CommentVO;
import com.example.demo.domain.BookUploadRecords;
import com.example.demo.domain.BorrowForms;
import com.example.demo.domain.Collection;
import com.example.demo.domain.User;
import com.example.demo.mapper.BookUploadRecordsMapper;
import com.example.demo.mapper.BorrowFormsMapper;
import com.example.demo.mapper.CollectionMapper;
import com.example.demo.service.RecordsService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：collection服务实现类
 * @date ：2021/3/24 20:36
 */
@Service
public class RecordsServiceImpl implements RecordsService {

    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private BorrowFormsMapper borrowFormsMapper;
    @Autowired
    private BookUploadRecordsMapper bookUploadRecordsMapper;

    /**
    * @Description: 返回所有书籍收藏记录
    * @Param: []
    * @return: java.util.List<com.example.demo.domain.Collection>
    * @Author: chenjiajun
    * @Date: 2021/3/25
    */
    @Override
    public CommentVO<Collection> getAllCollections() {
        List<Collection> l =collectionMapper.selectAll();
        return new CommentVO<Collection>(0,"",(long)l.size(),l);
    }

//    @Override
//    public CommentVO<Collection> getCollectionsByUserId(HttpServletRequest request) {
//        HttpSession s = request.getSession();
//        User u = (User) s.getAttribute("user");
////        System.out.println(u);
//        List<Collection> l = collectionMapper.selectByUserId(u.getUserId());
//        return new CommentVO<Collection>(0,"",(long)l.size(),l);
//    }

    /**
    * @Description: 为个人用户图书收藏记录提供分页查询
    * @Param: [page, limit]
    * @return: com.example.demo.ViewObject.CommentVO<com.example.demo.domain.Collection>
    * @Author: chenjiajun
    * @Date: 2021/4/1
    */
    @Override
    public CommentVO<Collection> getUserCollection(Integer page, Integer limit,HttpServletRequest request) {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("user");
        PageHelper.startPage(page,limit);
        List<Collection> l = collectionMapper.selectByUserId(u.getUserId());
        return new CommentVO<Collection>(0,"",(long)l.size(),l);
    }


    /**
    * @Description: 获取所有的书籍借阅订单记录
    * @Param: []
    * @return: com.example.demo.ViewObject.CommentVO<com.example.demo.domain.BorrowForms>
    * @Author: chenjiajun
    * @Date: 2021/3/31
    */
    @Override
    public CommentVO<BorrowForms> getAllBorrowForms() {
        List<BorrowForms> l =borrowFormsMapper.selectAll();
        return  new CommentVO<BorrowForms>(0,"",(long)l.size(),l);
    }

    @Override
    public BookUploadRecords uploadRecords(BookUploadRecords bookUploadRecords) {
        //先将记录插入表中
        bookUploadRecordsMapper.insertRecords(bookUploadRecords);
//        return bookUploadRecordsMapper.selectOne(bookUploadRecords);
        //再重新获取带id的记录对象
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        Date d = bookUploadRecords.getRecordsUpdateDatetime();
//        dateFormat.format(d);
//        System.out.println(bookUploadRecords.getRecordsId());
        return bookUploadRecords;
    }

    @Override
    public void updateUploadRecords(BookUploadRecords bookUploadRecords) {
        bookUploadRecordsMapper.updateByPrimaryKey(bookUploadRecords);
    }

    @Override
    public List<BookUploadRecords> getOrgUploadRecordsByStatus(Long orgid, String status) throws ParseException {
        //按条件查询到所有数据
        List<BookUploadRecords> list = bookUploadRecordsMapper.selectOrgUploadRecordsByStatus(orgid,status);
        //对查询到的对象进行处理，主要是时间转换和图片的地址拼接
        for (BookUploadRecords records : list) {
            //图片地址拼接
            String imgsrc = "/localImg/"+records.getRecordsBookCover();
            records.setRecordsBookCover(imgsrc);
            //时间转换
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date a = records.getRecordsCreatetime();
            records.setCreatetime(dateFormat.format(a));
        }
        return list;
    }

    public List<BookUploadRecords> getAllUploadRecords(){
        return bookUploadRecordsMapper.selectAll();
    }

}
