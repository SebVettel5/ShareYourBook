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
    /**
    * @Description: 向用户表插入新用户，返回结果
    * @Param: [user]
    * @return: int
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    int NewUserRegister(User user);
    
    /**
    * @Description: 用户登录，返回在user表中的查询结果
    * @Param: [account, password]
    * @return: com.example.demo.domain.User
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    User Login(Long phone, String password);


    User SelectUserByName(String userName);
}

