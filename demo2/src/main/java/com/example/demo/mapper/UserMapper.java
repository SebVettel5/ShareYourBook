package com.example.demo.mapper;

import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author ：chenjiajun
 * @description：用户Mapper接口
 * @date ：2021/2/1 17:00
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    int NewUserRegister(User user);
    User Login(String account, String password);
}

