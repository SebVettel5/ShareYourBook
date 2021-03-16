package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：chenjiajun
 * @description：定义访问HTML页面的映射
 * @date ：2021/2/23 22:27
 */
@Controller
public class PageController {
    @RequestMapping("/homepage.html")
    public String Hello(){
        return "homepage";
    }

    @RequestMapping("/login.html")
    public String Login(){
        return "login";
    }

    @RequestMapping("/about.html")
    public  String About(){
        return "about";
    }

    @RequestMapping("/borrowbook.html")
    public String BorrowBook(){
        return "borrowbook";
    }

    //忘记密码页面
    @RequestMapping("/getpassword.html")
    public String GetPassWord(){
        return "getpassword";
    }

    @RequestMapping("/news.html")
    public String News(){
        return "news";
    }

    @RequestMapping("/orgregister.html")
    public String OrgRegister(){
        return "orgregister";
    }

    @RequestMapping("/readercommunity.html")
    public String ReaderCommunity(){
        return "readercommunity";
    }

    @RequestMapping("/servicestatement.html")
    public String ServiceStatement(){
        return "servicestatement";
    }

    @RequestMapping("/uploadbook.html")
    public String UploadBook(){
        return "uploadbook";
    }

    //用户指导
    @RequestMapping("/userguide.html")
    public String UserGuide(){
        return "userguide";
    }

    @RequestMapping("/administratormain.html")
    public String AdminMain(){
        return "administratormain";
    }

    @RequestMapping("/welcome.html")
    public String Welcome(){
        return "welcome";
    }
}
