package com.example.demo.controller;

import com.example.demo.domain.Organization;
import com.example.demo.service.Impl.OrganizationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    /**
    * @Description: 查找所有组织，返回list集合
    * @Param: []
    * @return: java.util.List<com.example.demo.domain.Organization>
    * @Author: chenjiajun
    * @Date: 2021/2/25
    */
    @RequestMapping("/SelectAllOrganization")
    @ResponseBody
    public List<Organization> SelectAllOrganization(){
        return organizationServiceImpl.SelectAllOrganization();
    }

    /**
    * @Description: 组织管理员登录，返回组织对象
    * @Param: [account, password]
    * @return: com.example.demo.domain.Organization
    * @Author: chenjiajun
    * @Date: 2021/2/25
    */
    @PostMapping("/OrganizationLogin")
    public String OrgLogin(String account, String password, HttpSession session, RedirectAttributes redirectAttributes){
        return organizationServiceImpl.Login(account,password,session,redirectAttributes);
    }

    /**
    * @Description: 此映射是管理员收到机构申请的时候，对符合条件的进行添加
    * @Param: [orgname, orgpassword, orgmail, orgheadpic]
    * @return: int
    * @Author: chenjiajun
    * @Date: 2021/2/25
    */
//    @RequestMapping("/RegisterNewOrganization")
//    @ResponseBody
//    public int RegisterNewOrganization(String orgname,String orgpassword,String orgmail,String orgheadpic){
//        return organizationServiceImpl.RegisterNewOrganization(orgname,orgpassword,orgmail,orgheadpic);
//    }

    /**
    * @Description: 批量删除组织用户
    * @Param: [list]
    * @return: int
    * @Author: chenjiajun
    * @Date: 2021/2/25
    */
    @RequestMapping("/DeleteOrganizations")
    @ResponseBody
    public int  DeleteOrganizations(List<Organization> list){
        return organizationServiceImpl.DeleteOrganization(list);
    }
}
