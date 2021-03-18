package com.example.demo.service;

import org.springframework.ui.Model;
import com.example.demo.domain.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface UserService {
    //查找全部用户
    List<User> SelectAllUser();

    //按UserPhone查找用户
    User SelectUserByPhone(String Phone);

    //添加用户
    String UserRegister(String phone,String username, String userpassword, String email, Model model, RedirectAttributes redirectAttributes);

    //按照id删除用户
    int DeleteUserById(String id);

    //更新用户信息
    int UpdateUserInformation(User u);

    String UserLogin(String account, String password, Model model, RedirectAttributes redirectAttributes);
}
