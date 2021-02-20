package com.example.demo.controller;

import com.example.demo.domain.Administrator;
import com.example.demo.domain.Organization;
import com.example.demo.domain.User;
import com.example.demo.service.Impl.AdministratorServiceImpl;
import com.example.demo.service.Impl.OrganizationServiceImpl;
import com.example.demo.service.Impl.UserServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@MapperScan("com.example.demo.mapper.UserMapper")
@RestController
public class controller {
        //----------以下为变量声明----------
        //Spring自动封装bean
        //user服务实现类
        @Autowired
        private UserServiceImpl userServiceImpl ;
        //Administrator服务实现类
        @Autowired
        private AdministratorServiceImpl administratorServiceImpl;
        //OrganizationServiceImpl服务实现类
        @Autowired
        private OrganizationServiceImpl organizationServiceImpl;


        //-----------以下为对User表进行操作------------------
        
        /**
        * @Description: 查找所有用户信息
        * @Param: []
        * @return: java.util.List<com.example.demo.domain.User>
        * @Author: chenjiajun
        * @Date: 2021/2/19
        */
        @RequestMapping("/SelectAllUser")
        @ResponseBody//返回类型为json
        public List<User> SelectAll() {
            return userServiceImpl.SelectAllUser();
        }
        
        /**
        * @Description: 按照id删除
        * @Param: []
        * @return: int
        * @Author: chenjiajun
        * @Date: 2021/2/19
        */
        @RequestMapping("/DeleteUserById")
        @ResponseBody
        public int DeleteUserById(String id){
            return userServiceImpl.DeleteUserById(id);
        }

        /**
        * @Description: 更新user信息
        * @Param: [u]
        * @return: int
        * @Author: chenjiajun
        * @Date: 2021/2/19
        */
        @RequestMapping("/UpdateUserById")
        @ResponseBody
        public  int UpdateUserById(User u){
            return  userServiceImpl.UpdateUserInformation(u);
        }

        /**
        * @Description: 用户使用账号密码登录
        * @Param: [account, password]
        * @return: com.example.demo.domain.User
        * @Author: chenjiajun
        * @Date: 2021/2/19
        */
        @RequestMapping("/UserLogin")
        @ResponseBody
        public User UserLogin(String account,String password){
            return userServiceImpl.UserLogin(account,password);
        }

        /**
        * @Description: 用户初次注册时，通过手机号和密码直接注册
        * @Param: [userphone, userpassword]
        * @return: com.example.demo.domain.User
        * @Author: chenjiajun
        * @Date: 2021/2/19
        */
        @RequestMapping("/UserRegister")
        @ResponseBody
        public User UserRegister(String userphone,String userpassword){
            User u = new User(userphone,userpassword);
            return  userServiceImpl.UserRegister(u);
        }





        //----------以下为对Administrator表进行操作----------------

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


        //-----------以下为对organization表进行操作

        @RequestMapping("/SelectAllOrganization")
        @ResponseBody
        public List<Organization> SelectAllOrganization(){
            return organizationServiceImpl.SelectAllOrganization();
        }





        //------------框架测试--------
    /**
     * @Description: 测试框架启动
     * @Param: []
     * @return: java.lang.String
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/1111")
    @ResponseBody
    public String Hello(){
        return "hello";
    }
}
