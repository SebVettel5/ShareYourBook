package com.example.demo.controller;

import com.example.demo.domain.Organization;
import com.example.demo.service.Impl.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ：chenjiajun
 * @description：涉及组织操作映射
 * @date ：2021/2/24 16:40
 */
@Controller
public class OrganizationController {
    //OrganizationServiceImpl服务实现类
    @Autowired
    private OrganizationServiceImpl organizationServiceImpl;


    @RequestMapping("/SelectAllOrganization")
    @ResponseBody
    public List<Organization> SelectAllOrganization(){
        return organizationServiceImpl.SelectAllOrganization();
    }
}
