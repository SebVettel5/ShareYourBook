package com.example.demo.service.Impl;

import com.example.demo.domain.Administrator;
import com.example.demo.domain.Organization;
import com.example.demo.mapper.OrganizationMapper;
import com.example.demo.service.OrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：Organization服务实现类
 * @date ：2021/2/20 16:45
 */
@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {
    //-----------------变量声明------------------
    @Resource
    private OrganizationMapper organizationMapper;

    //------------------方法声明------------------
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

    /**
    * @Description: 通过用户名和账号登录，返回查询到的组织对象,跳转到信息页面，如果查不到，返回到登录页面
    * @Param: [account, password]
    * @return: org.springframework.web.servlet.ModelAndView
    * @Author: chenjiajun
    * @Date: 2021/2/25
    */
    @Override
    public String Login(String account, String password, HttpSession session, RedirectAttributes redirectAttributes) {
        Organization organization = organizationMapper.Login(account,password);
//        ModelAndView mv = new ModelAndView();
        //查询不到对象时
        if(organization == null){
//            mv.setViewName("login");
//            mv.addObject("error");
              redirectAttributes.addFlashAttribute("errorInfo","账号或者密码错误，请确认后重试");
            return "redirect:/login";
        }
        //进行参数传递
        organization.setOrgPassword("");
        session.setAttribute("org",organization);
        session.setAttribute("userType","org");
//        mv.addObject("org",organization);
//        mv.setViewName("readercommunity");
        return "redirect:/org/orgmanage";
    }

    /**
    * @Description: 接受传参，向表中添加新的组织，返回插入结果
    * @Param: [orgname, orgpassword, orgmail, orgheadpic]
    * @return: int
    * @Author: chenjiajun
    * @Date: 2021/2/25
    */
//    @Override
//    public int RegisterNewOrganization(String orgname, String orgpassword, String orgmail, String orgheadpic) {
//        Organization organizationTemp = new Organization(orgname,orgpassword,orgmail,orgheadpic);
//        return organizationMapper.insert(organizationTemp);
//    }

    @Override
    public int DeleteOrganization(List<Organization> list) {
        return 0;
    }

//    @Override
//    public int DeleteOrganization(List<Organization> list) {
//        int res = 0;                        //记录成功删除的组织用户数目
//        for (list: ;org) {
//            if(organizationMapper.delete(org) == 1)res+;
//        }
//
//
//
////        int[] a ={1,2,3,4};
////        for (a:
////             ) {
////
////        }
//
//
//
//        return res;
//    }
}
