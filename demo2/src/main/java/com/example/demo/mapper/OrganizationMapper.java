package com.example.demo.mapper;


import com.example.demo.domain.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;


@Mapper
@Component
public interface OrganizationMapper extends BaseMapper<Organization> {
    /**
    * @Description: 组织用户登录方法
    * @Param: [account, password]
    * @return: com.example.demo.domain.Organization
    * @Author: chenjiajun
    * @Date: 2021/2/25
    */
    Organization Login(String account, String password);

    /**
    * @Description: 获取一个正在合法运营的注册机构
    * @Param: [orgId]
    * @return: com.example.demo.domain.Organization
    * @Author: chenjiajun
    * @Date: 2021/5/8
    */
    Organization getLegalityOrg(Long orgId,float orgPoints);
}
