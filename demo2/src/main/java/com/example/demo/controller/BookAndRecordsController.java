package com.example.demo.controller;

import com.example.demo.ViewObject.CommentVO;
import com.example.demo.domain.BookUploadRecords;
import com.example.demo.domain.BorrowForms;
import com.example.demo.domain.Collection;
import com.example.demo.domain.Organization;
import com.example.demo.service.Impl.RecordsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：书籍和与书籍相关记录的控制器
 * @date ：2021/3/24 20:31
 */
@Controller
public class BookAndRecordsController {

    @Autowired
    private RecordsServiceImpl recordsService;

    /**
    * @Description: 获取所有的书籍收藏记录，以json的格式返回
    * @Param: []
    * @return: java.util.List<com.example.demo.domain.Collection>
    * @Author: chenjiajun
    * @Date: 2021/3/24
    */
    @RequestMapping("/collection/getAll")
    @ResponseBody
    public CommentVO<Collection> getAllCollection(){
        return recordsService.getAllCollections();
    }

    /**
    * @Description: 获取所有书籍借阅订单
    * @Param: [status]
    * @return: com.example.demo.ViewObject.CommentVO<com.example.demo.domain.BorrowForms>
    * @Author: chenjiajun
    * @Date: 2021/3/31
    */
    @RequestMapping("/BorrowForms/getAll")
    @ResponseBody
    public CommentVO<BorrowForms> getAllForms(String status){
        return recordsService.getAllBorrowForms();
    }

    /***
    * @Description: 按照id获取用户所有的收藏并分页
    * @Param: [request]
    * @return: com.example.demo.ViewObject.CommentVO<com.example.demo.domain.Collection>
    * @Author: chenjiajun
    * @Date: 2021/4/10
    */
    @RequestMapping("/collection/getUserCollections")
    @ResponseBody
    public CommentVO<Collection> getAllCollectionByUid( @RequestParam("page")Integer page,
                                                        @RequestParam("limit")Integer limit,
                                                        HttpServletRequest request
                                                      ){
        return recordsService.getUserCollection(page,limit,request);
    }

    /***
    * @Description: 按照orgid来请求正在审核的记录返回给innnerhtml
    * @Param: [model, httpSession]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/4/15
    */
    @RequestMapping("/org/checkinglist")
    public String orgCheckingList(Model model,HttpSession httpSession) throws ParseException {
        //获取机构用户对象
        Organization org = (Organization) httpSession.getAttribute("org");
        model.addAttribute("records_list",recordsService.getOrgUploadRecordsByStatus(org.getOrgId(),"审核中"));
        return "org/checkinglist";
    }
}
