package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.Organization;
import com.example.demo.service.Impl.BookServiceImpl;
import com.example.demo.service.Impl.OrganizationServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Autowired
    private BookServiceImpl bookServiceImpl;

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

    /**
    * @Description: 获取某个合法机构所有在售书籍信息
    * @Param: [model, orgId, pageNum]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/9
    */
    @RequestMapping("/store/getOrgStore")
    public String getOrgStore(Model model,Long orgId, int pageNum){
        //首先验证一下注册的合法性,对数据库中存在的，经营状态为在营的，已经完成注册验证的机构，才返回商家图书，否则到404页面
        Organization org = organizationServiceImpl.checkLegality(orgId);
        if(null == org){
            //若机构不符合经营状态，跳转到404状态
            return "error/404";
        }
        //获取所有在售图书
        PageInfo<Book> list = bookServiceImpl.getBooksByIdAndStatus(orgId,"onSale",true,pageNum);
        model.addAttribute("result",list);
        model.addAttribute("storeOrg",org);
        return "orgStore";
    }

    @RequestMapping("/store/searchBooks")
    public String storeSearchBooks(Model model,Long orgId,int pageNum,String searchString){
        Organization org = organizationServiceImpl.checkLegality(orgId);
        if(null == org){
            //若机构不符合经营状态，跳转到404状态
            return "error/404";
        }
        PageInfo<Book> list = bookServiceImpl.fuzzySearch(searchString, pageNum,orgId);
        model.addAttribute("result",list);
        model.addAttribute("storeOrg",org);
        model.addAttribute("searchString",searchString);
        return "orgStore";
    }
}
