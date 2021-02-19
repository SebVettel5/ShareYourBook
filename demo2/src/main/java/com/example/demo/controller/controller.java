package com.example.demo.controller;

import com.example.demo.domain.Administrator;
import com.example.demo.domain.User;
import com.example.demo.service.AdministratorService;
import com.example.demo.service.Impl.UserServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@MapperScan("com.example.demo.mapper.UserMapper")
@RestController
public class controller {
        //Spring自动封装bean
//        @Autowired
//        @Autowired
        @Autowired
        private UserServiceImpl userServiceImpl ;

        @Autowired
        private AdministratorService administratorService;

        //以下为对User表进行操作

        //1.查找所有用户数据
        @RequestMapping("/SelectAllUser")
        @ResponseBody//返回类型为json
        public List<User> SelectAll(){
            List<User> list = userServiceImpl.SelectAllUser();
            return list;
        }

        //2.按UserPhone查找用户
        @RequestMapping("/SelectUserByPhone")
        @ResponseBody
        public User LoginCheck(){
            String id = "13789083230";
            User u = userServiceImpl.SelectUserByPhone(id);
            return u;
        }

        //3.用户注册
        @RequestMapping("/InsertUser")
        @ResponseBody
        public int InsertUser(){
            User u = new User();
            u.setUserName("tom");
            u.setUserPhone("13712345679");
            u.setUserPassword("123456");
            int res = userServiceImpl.UserRegister(u);
            return res;
        }


        //4.按id删除用户
        @RequestMapping("/DeleteUserById")
        @ResponseBody
        public int DeleteUserById(){
            int res = userServiceImpl.DeleteUserById("1000025");
            return res;
        }

        //5.按id修改用户
        @RequestMapping("/UpdateUserById")
        @ResponseBody
        public  int UpdateUserById(){
            User u = new User();
            u.setUserId("1000024");
            u.setUserName("王二");
            return  userServiceImpl.UpdateUserInformation(u);
        }

        /**
        * @Description: 用户使用账号密码登录，登录成功返回一个包装好了的用户对象，否则返回一个
         *              空对象，前端拒绝登录
        * @Param: [account, password]
        * @return: com.example.demo.domain.User
        * @Author: chenjiajun
        * @Date: 2021/2/19
        */
        @RequestMapping("/UserLogin")
        @ResponseBody
        public User UserLogin(String account,String password){
//            System.out.println(account+" "+password);
            User u = userServiceImpl.UserLogin(account,password);
//            System.out.println("result is :"+userServiceImpl.UserLogin(account,password).toString());
            return u;
        }







        //测试框架访问
        @RequestMapping("/1111")
        @ResponseBody
        public String Hello(){
            return "hello";
        }


        //以下为对Administrator表进行操作
        @RequestMapping("/SelectAllAdmin")
        @ResponseBody
        public List<Administrator> showAll(){
            List<Administrator> list = administratorService.SellectAll();
            return list;
        }

}
