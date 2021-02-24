package com.example.demo.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description:测试框架运行
* @Author: chenjiajun
* @Date: 2021/2/24
*/

@Controller
@MapperScan("com.example.demo.mapper.UserMapper")
@RestController
public class controller {
        //----------以下为变量声明----------
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



}
