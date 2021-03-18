package com.example.demo.controller;

import com.example.demo.exception.NotFindException;
import com.example.demo.util.GeneralUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
* @Description:测试框架运行
* @Author: chenjiajun
* @Date: 2021/2/24
*/

@Controller
@MapperScan("com.example.demo.mapper.UserMapper")
//@RestController
public class controller {
        //----------以下为变量声明----------
    GeneralUtil generalUtil = new GeneralUtil();
        //------------框架测试--------
    /**
     * @Description: 测试框架启动
     * @Param: []
     * @return: java.lang.String
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
     @RequestMapping("/hello")
     public String hello(){
         return "hello";
     }

     @RequestMapping("getcode")
    public String GetCode(){
         return generalUtil.GetCode();
     }

     /**
     * @Description: 测试错误springboot自动拦截异常跳转页面
     * @Param: []
     * @return: java.lang.String
     * @Author: chenjiajun
     * @Date: 2021/3/8
     */
     @RequestMapping("/test404")
    public String TestError()
     {
         int a = 10/0;
//         String blog = null;
//         if(blog == null)throw new NotFindException("dee");
         return "homepage";
     }

     @RequestMapping("/test")
    public  String Test(){
         return "test";
     }

//     @RequestMapping("/getCheckcode")
//    public  void GetCheckCode(RedirectAttributes redirectAttributes){
//         redirectAttributes.addFlashAttribute("code",generalUtil.GetCode());
//     }

}
