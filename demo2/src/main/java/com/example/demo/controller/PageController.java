package com.example.demo.controller;

import com.example.demo.domain.BookUploadRecords;
import com.example.demo.service.Impl.RecordsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ：chenjiajun
 * @description：定义访问HTML页面的映射
 * @date ：2021/2/23 22:27
 */
@Controller
public class PageController {
    @Autowired
    private RecordsServiceImpl recordsService;


    @RequestMapping("/homepage")
    public String Hello(){
        return "homepage";
    }

    @RequestMapping("/login")
    public String Login(){
        return "login";
    }

    @RequestMapping("/about")
    public  String About(){
        return "about";
    }

    @RequestMapping("/borrowbook")
    public String BorrowBook(){
        return "borrowbook";
    }

    //忘记密码页面
    @RequestMapping("/getpassword")
    public String GetPassWord(){
        return "getpassword";
    }

    @RequestMapping("/news")
    public String News(){
        return "news";
    }

    @RequestMapping("/orgregister")
    public String OrgRegister(){
        return "orgregister";
    }

    @RequestMapping("/readercommunity")
    public String ReaderCommunity(){
        return "readercommunity";
    }

    @RequestMapping("/servicestatement")
    public String ServiceStatement(){
        return "servicestatement";
    }

    @RequestMapping("/org/uploadbookpage")
    public String UploadBook(){
        return "uploadbook";
    }

    //用户指导
    @RequestMapping("/userguide")
    public String UserGuide(){
        return "userguide";
    }

    @RequestMapping("/admin/administratormain")
    public String AdminMain(){
        return "admin/administratormain";
    }

    @RequestMapping("/welcome")
    public String Welcome(){
        return "welcome";
    }

    //页面通用fragments
    @RequestMapping("/fragments")
    public String Fragments(){
        return "fragments";
    }

    @RequestMapping("/personalpage")
    public String PersonalPage(){
        return "personalpage";
    }

    @RequestMapping("/tabletest")
    public String tabletest(){
        return "tabletest";
    }

    @RequestMapping("/imgtest")
    public String imgtest(){
        return  "imgtest";
    }

    @RequestMapping("/org/orgmanage")
    public  String orgManage(){
        return "org/orgmanagepage";
    }

    @RequestMapping("/org/uploadrecords")
    public String orgUploadRecords(){
        return "org/uploadrecords";
    }

    @RequestMapping("/org/allrecords")
    public String orgAllrecords(){
        return "org/allrecords";
    }

}
