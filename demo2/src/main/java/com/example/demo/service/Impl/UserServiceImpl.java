package com.example.demo.service.Impl;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：用户服务实现类
 * @date ：2021/2/1 17:18
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    //Resource 自动ByName进行注解
    private UserMapper userMapper;

    //实现查询全部用户
    @Override
    public List<User> SelectAllUser() {
        return userMapper.selectAll();
    }

    //按UserPhone查询用户
    @Override
    public User SelectUserByPhone(String Phone) {
        return userMapper.selectByPrimaryKey(Phone);
    }

    //添加用户
    @Override
    public User UserRegister(User user) {
        int res = userMapper.NewUserRegister(user);
        if(res == 0)System.out.println("插入失败！");
        //新建用户后，重新再从表中查询到这个用户，返回初始设定的用户信息
        return SelectUserByPhone(user.getUserPhone());
    }

    //按id删除用户
    @Override
    public int DeleteUserById(String id) {
        User u = new User();
        u.setUserId(id);
        return userMapper.delete(u);
    }

    //更新用户信息
    @Override
    public int UpdateUserInformation(User u) {
        int res = userMapper.updateByPrimaryKeySelective(u);
        return res;
    }

    //登录并返回用户对象
    @Override
    public User UserLogin(String account, String password) {
        User u = userMapper.Login(account,password);
        if(u == null)System.out.println("空对象，登录错误");
        return u;
    }


}
