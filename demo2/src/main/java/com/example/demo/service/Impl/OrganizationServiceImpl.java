package com.example.demo.service.Impl;

import com.example.demo.domain.Administrator;
import com.example.demo.domain.Organization;
import com.example.demo.mapper.OrganizationMapper;
import com.example.demo.service.OrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：Organization服务实现类
 * @date ：2021/2/20 16:45
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    @Resource
    private OrganizationMapper organizationMapper;

    
    /**
    * @Description: 查询所有机构信息，返回集合
    * @Param: []
    * @return: java.util.List<com.example.demo.domain.Organization>
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    @Override
    public List<Organization> SelectAllOrganization() {
        return organizationMapper.selectAll();
    }
}
