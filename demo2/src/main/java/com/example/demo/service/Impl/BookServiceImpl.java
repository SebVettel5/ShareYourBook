package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.example.demo.domain.Book;
import com.example.demo.mapper.BooksMapper;
import com.example.demo.service.BookService;
import com.example.demo.util.GeneralUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：chenjiajun
 * @description：TODO
 * @date ：2021/4/12 22:40
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BooksMapper booksMapper;


    /**
    * @Description: 按照状态分页查询指定机构的在库图书信息
    * @Param: [orgId, status, dataStatus, pageNum]
    * @return: com.github.pagehelper.PageInfo<com.example.demo.domain.Book>
    * @Author: chenjiajun
    * @Date: 2021/5/4
    */
    @Override
    public PageInfo<Book> getBooksByIdAndStatus(Long orgId, String status, Boolean dataStatus, int pageNum) {
        PageHelper.startPage(pageNum, GeneralUtil.bookListPageSize);
        List<Book> list = booksMapper.fuzzySearchBooks("%%","onSale",true,orgId.toString());
//        System.out.println(1+" "+list);
        return GeneralUtil.preBookList(list,GeneralUtil.localBooksCoverLocation);
    }

    /**
    * @Description: 改变在库书籍的状态
    * @Param: [orgId, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/5
    */
    @Override
    public String changeStatus(Long orgId, String[] arr) {
        //获取目标状态值
        String status = arr[0];
        //记录成功操作的数据条数
        int res = 0;
        //验证目标状态值合法性
        if (!GeneralUtil.bookStatusTypeSet.contains(status)){
            return "操作无效，请刷新页面后重试！";
        }else {
            for (int i = 1;i < arr.length;i++){
                res += booksMapper.changeStatusById(new Long(arr[i]),orgId,status);
            }
        }
        return "选中"+(arr.length-1)+"条数据，完成"+res+"条申请撤销";
    }

    /**
    * @Description: 保存对在库书籍的修改
    * @Param: [orgId, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/5
    */
    @Override
    public String saveChanges(Long orgId, String[] arr) {
        //首先验证书籍的合法性
        //arr[]={书籍id,书籍数量,书籍价格,书籍简述}
        Long bookId = new Long(arr[0]);
        Long bookAmount = new Long(arr[1]);
        Double bookPrice = new Double(arr[2]);

        String bookDescription = arr[3];
        //处理结果
        int res = 0;
        //非法数据拒绝修改
        if (booksMapper.checkLegality(orgId,new Long(arr[0])) == null){
            return "非法数据，操作无效！请稍后重试.";
        }else{
            //对合法数据进行修改
            res = booksMapper.updateByPrimaryKeySelective(new Book(bookId,bookPrice,bookAmount,bookDescription));
            booksMapper.statusTrigger(bookId);
        }
        return res == 1 ? "修改保存成功！":"修改保存失败！请稍后重试.";
    }

    /**
    * @Description: 分页查询所有书籍
    * @Param: [orgId, pageNum]
    * @return: com.github.pagehelper.PageInfo<com.example.demo.domain.Book>
    * @Author: chenjiajun
    * @Date: 2021/5/6
    */
    @Override
    public PageInfo<Book> selectAllByOrgId(Long orgId, int pageNum) {
        //开始分页
        PageHelper.startPage(pageNum,GeneralUtil.bookListPageSize);
        List<Book> list = booksMapper.selectAllByOrgId(orgId);
        return GeneralUtil.preBookList(list,GeneralUtil.localBooksCoverLocation);
    }

    @Override
    public String delete(Long orgId, Long bookId) {
        if ( booksMapper.deleteById(orgId,bookId) == 1){
            return "删除成功！";
        }
            else return "删除失败，请稍后重试！";
    }

    /**
    * @Description: 按照状态分页获取全部书籍
    * @Param: [pageNum]
    * @return: com.github.pagehelper.PageInfo<com.example.demo.domain.Book>
    * @Author: chenjiajun
    * @Date: 2021/5/6
    */
    @Override
    public PageInfo<Book> getAllOnSaleBook(int pageNum) {
        PageHelper.startPage(pageNum,GeneralUtil.storeListSize);
        List<Book> list = booksMapper.selectAllByStatus("onSale");
        return GeneralUtil.preBookList(list,GeneralUtil.localBooksCoverLocation);
    }

    /**
    * @Description: 按照id获取书籍,并对图片信息进行处理
    * @Param: [bookId]
    * @return: com.example.demo.domain.Book
    * @Author: chenjiajun
    * @Date: 2021/5/6
    */
    @Override
    public Book getBook(Long bookId) {
        Book book = booksMapper.getBookById(bookId);
        GeneralUtil.preBookCover(book,GeneralUtil.localBooksCoverLocation);
        return book;
    }

    /***
    * @Description: 按照字符串条件模糊查询符合的书籍
     * 用字符串匹配书籍的名字，作者，出版社，店铺名字
    * @Param: [searchString, pageNum]
    * @return: com.github.pagehelper.PageInfo<com.example.demo.domain.Book>
    * @Author: chenjiajun
    * @Date: 2021/5/7
    */
    @Override
    public PageInfo<Book> fuzzySearch(String searchString, int pageNum,Long orgId) {
        PageHelper.startPage(1,GeneralUtil.storeListSize);
        List<Book> list = booksMapper.fuzzySearchBooks("%"+searchString+"%","onSale",true,null);
        return GeneralUtil.preBookList(list,GeneralUtil.localBooksCoverLocation);
    }

    /**
    * @Description: 订单获取书籍信息
    * @Param: [bookId]
    * @return: com.example.demo.domain.Book
    * @Author: chenjiajun
    * @Date: 2021/5/12
    */
    @Override
    public Book getOderBook(Long bookId) {
        Book book = booksMapper.orderBookInfo(bookId);
        GeneralUtil.preBookCover(book,GeneralUtil.localBooksCoverLocation);
        return book;
    }


}
