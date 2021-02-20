package com.example.demo.mapper;

import com.example.demo.domain.Administrator;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface AdministratorMapper extends BaseMapper<Administrator> {
    /**
    * @Description: 管理员登录方法
    * @Param: [account, password]
    * @return: com.example.demo.domain.Administrator
    * @Author: chenjiajun
    * @Date: 2021/2/19
    */
    Administrator Login(String account, String password);
    
    /**
    * @Description: 添加管理员方法
    * @Param: [administrator]
    * @return: int
    * @Author: chenjiajun
    * @Date: 2021/2/19
    */
    int NewAdminRegister(Administrator administrator);

    /**
    * @Description: 按照管理员姓名查找管理员全部信息
    * @Param: [adminName]
    * @return: com.example.demo.domain.Administrator
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    Administrator SelectAdminByName(String adminName);
}
