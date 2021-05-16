package com.example.demo.service;

import com.example.demo.domain.Deposits;
import org.springframework.ui.Model;
import com.example.demo.domain.User;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    //查找全部用户
    List<User> SelectAllUser();

    //按UserPhone查找用户
    User SelectUserByPhone(Long Phone);

    //添加用户
    String UserRegister(Long phone,
                        String username,
                        String userpassword,
                        String email,
                        Model model,
                        RedirectAttributes redirectAttributes,
                        HttpSession session);

    //按照id删除用户
    int DeleteUserById(Long id);

    //更新用户信息
    int UpdateUserInformation(User u);

    String UserLogin(String phone, String password, Model model, RedirectAttributes redirectAttributes, HttpSession session);

    User login(String account, String password);

}
