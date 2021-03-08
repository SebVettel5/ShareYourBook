package com.example.demo.mapper;


import com.example.demo.domain.Organization;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;


@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {
    /**
    * @Description: 组织用户登录方法
    * @Param: [account, password]
    * @return: com.example.demo.domain.Organization
    * @Author: chenjiajun
    * @Date: 2021/2/25
    */
    Organization Login(String account, String password);
}
