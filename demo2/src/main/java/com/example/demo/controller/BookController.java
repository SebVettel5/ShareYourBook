package com.example.demo.controller;

import com.example.demo.ViewObject.OrdersWrapper;
import com.example.demo.domain.Book;
import com.example.demo.domain.Organization;
import com.example.demo.domain.User;
import com.example.demo.service.Impl.AddressServiceImpl;
import com.example.demo.service.Impl.BookServiceImpl;
import com.example.demo.service.Impl.UserServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author ：chenjiajun
 * @description：图书控制类
 * @date ：2021/5/4 0:26
 */
@Controller
public class BookController {
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private AddressServiceImpl addressServiceImpl;

    
    /**
    * @Description: 按照机构id和状态对在库书籍进行分页查询
    * @Param: [model, session, status, pageNum]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/4
    */
    @RequestMapping("/org/getBooksByStatus")
    public String getBooksByStatus(Model model, HttpSession session,@RequestParam String status,@RequestParam int pageNum){
        Organization org =  (Organization) session.getAttribute("org");
        PageInfo<Book> list;
        if (status.equals("allBooks")){
            list = bookServiceImpl.selectAllByOrgId(org.getOrgId(),pageNum);
        }else {
            list = bookServiceImpl.getBooksByIdAndStatus(org.getOrgId(),status,true,pageNum);
        }
        //将查询结果放入其中
        model.addAttribute("result",list);
        //放入当前查询的状态
        model.addAttribute("status",status);
        return "org/putaway";
    }

    /**
    * @Description: 按照id修改书籍上架状态
    * @Param: [session, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/5
    */
    @RequestMapping("/org/changeBookStatus")
    @ResponseBody
    public String changeBookStatus(HttpSession session, @RequestBody String[] arr){
        Organization org = (Organization) session.getAttribute("org");
        return bookServiceImpl.changeStatus(org.getOrgId(),arr);
    }

    /**
    * @Description: 修改上级图书的信息，并返回处理结果
    * @Param: [session, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/5
    */
    @RequestMapping("/org/saveChanges")
    @ResponseBody
    public String saveChanges(HttpSession session,@RequestBody String[] arr){
        Organization org = (Organization) session.getAttribute("org");
        return bookServiceImpl.saveChanges(org.getOrgId(),arr);
    }


    @RequestMapping("/org/deleteBook")
    @ResponseBody
    public String deleteBook(HttpSession session,@RequestBody String[] arr){
        Organization org = (Organization) session.getAttribute("org");
        return bookServiceImpl.delete(org.getOrgId(),new Long(arr[0]));
    }

    /**
    * @Description: 获取全部在售书籍
    * @Param: [model, session, pageNum]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/6
    */
    @RequestMapping("/getAllBooks")
    public String getAllBooks(Model model,int pageNum){
        PageInfo<Book> list = bookServiceImpl.getAllOnSaleBook(pageNum);
        model.addAttribute("result",list);
        model.addAttribute("searchString","");
        return "bookStore";
    }

    /**
    * @Description: 进入书籍详情
    * @Param: [model, bookId]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/6
    */
    @RequestMapping("/bookItemInfo")
    public String bookItemInfo(Model model,Long bookId){
        Book book = bookServiceImpl.getBook(bookId);
        if (null != book)
            model.addAttribute("book",book);
        else{
            return "error/404";
        }
        return "bookInfo";
    }



    /**
    * @Description: 获取全站图书
    * @Param: [model, searchString, pageNum]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/9
    */
    @RequestMapping("/searchForBooks")
    public String searchForBooks(Model model,String searchString,int pageNum){
        //获取全站图书时，使用动态sql，则置orgId为空
        PageInfo<Book> list = bookServiceImpl.fuzzySearch(searchString,pageNum,null);
        model.addAttribute("result",list);
        model.addAttribute("searchString",searchString);
        return "bookStore";
    }
}
