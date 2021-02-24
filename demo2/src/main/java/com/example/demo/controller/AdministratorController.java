package com.example.demo.controller;

import com.example.demo.domain.Administrator;
import com.example.demo.service.Impl.AdministratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ：chenjiajun
 * @description：涉及管理员映射
 * @date ：2021/2/24 16:37
 */
@Controller
public class AdministratorController {
    //Administrator服务实现类
    @Autowired
    private AdministratorServiceImpl administratorServiceImpl;

    /**
     * @Description: 查询所有管理员信息，返回List表
     * @Param: []
     * @return: java.util.List<com.example.demo.domain.Administrator>
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/SelectAllAdmin")
    @ResponseBody
    public List<Administrator> showAll(){
        return administratorServiceImpl.SelectAll();
    }

    /**
     * @Description: 管理员使用账号密码登录
     * @Param: [account, password]
     * @return: com.example.demo.domain.Administrator
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/AdministratorLogin")
    @ResponseBody
    public  Administrator AdminLogin(String account,String password){
        return administratorServiceImpl.AdminLogin(account,password);
    }

    /**
     * @Description: 向administrator表中添加管理员，并返回含有默认信息的管理员对象
     * @Param: [adminName, adminSecureLevel, adminPassword]
     * @return: com.example.demo.domain.Administrator
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/AdministratorRegister")
    @ResponseBody
    public Administrator AdminRegister(Administrator operator,String adminName,String adminSecureLevel,String adminPassword){
//            System.out.println(adminName);
        return administratorServiceImpl.AdminRegister(operator,adminName,Integer.valueOf(adminSecureLevel),adminPassword);
    }

    /**
     * @Description: 删除管理员用户
     * @Param: [operator, target]
     * @return: java.lang.String
     * @Author: chenjiajun
     * @Date: 2021/2/20
     */
    @RequestMapping("/DeleteAdministrator")
    @ResponseBody
    public String DeleteAdministrator(Administrator operator,Administrator target){
        return administratorServiceImpl.DeleteAdministrator(operator,target);
    }

    /**
     * @Description: 修改管理员信息
     * @Param: [administrator]
     * @return: int
     * @Author: chenjiajun
     * @Date: 2021/2/20
     */
    @RequestMapping("/UpdateAdministrator")
    @ResponseBody
    public String UpdateAdministrator(Administrator operator,Administrator target){
        return administratorServiceImpl.UpdateAdministrator(operator,target);
    }
}
