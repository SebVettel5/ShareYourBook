package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ：chenjiajun
 * @description：涉及用户的操作控制映射
 * @date ：2021/2/24 16:34
 */
@Controller
public class UserController {
    //Spring自动封装bean
    //user服务实现类
    @Autowired
    private UserServiceImpl userServiceImpl ;

    /**
     * @Description: 查找所有用户信息
     * @Param: []
     * @return: java.util.List<com.example.demo.domain.User>
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/SelectAllUser")
    @ResponseBody//返回类型为json
    public List<User> SelectAll() {
        return userServiceImpl.SelectAllUser();
    }

    /**
     * @Description: 按照id删除
     * @Param: []
     * @return: int
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/DeleteUserById")
    @ResponseBody
    public int DeleteUserById(String id){
        return userServiceImpl.DeleteUserById(id);
    }

    /**
     * @Description: 更新user信息
     * @Param: [u]
     * @return: int
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/UpdateUserById")
    @ResponseBody
    public  int UpdateUserById(User u){
        return  userServiceImpl.UpdateUserInformation(u);
    }

    /**
     * @Description: 用户使用账号密码登录
     * @Param: [account, password]
     * @return: com.example.demo.domain.User
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/UserLogin")
    @ResponseBody
    public User UserLogin(String account,String password){
        return userServiceImpl.UserLogin(account,password);
    }

    /**
     * @Description: 用户初次注册时，通过手机号和密码直接注册
     * @Param: [userphone, userpassword]
     * @return: com.example.demo.domain.User
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/UserRegister")
    @ResponseBody
    public User UserRegister(String userphone,String userpassword){
        User u = new User(userphone,userpassword);
        return  userServiceImpl.UserRegister(u);
    }
}