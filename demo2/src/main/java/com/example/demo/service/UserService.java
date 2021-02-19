package com.example.demo.service;

import com.example.demo.domain.User;

import java.util.List;

public interface UserService {
    //查找全部用户
    List<User> SelectAllUser();

    //按UserPhone查找用户
    User SelectUserByPhone(String Phone);

    //添加用户
    int UserRegister(User user);

    //按照id删除用户
    int DeleteUserById(String id);

    //更新用户信息
    int UpdateUserInformation(User u);

    User UserLogin(String account, String password);
}