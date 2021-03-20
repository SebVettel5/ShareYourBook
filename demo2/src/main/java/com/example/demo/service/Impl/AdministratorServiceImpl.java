package com.example.demo.service.Impl;

import com.example.demo.domain.Administrator;
import com.example.demo.mapper.AdministratorMapper;
import com.example.demo.service.AdministratorService;
import com.example.demo.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：管理员服务实现类
 * @date ：2021/2/3 14:11
 */
@Service
@Transactional
public class AdministratorServiceImpl implements AdministratorService {
    //-------声明部分-----
    @Resource
    private AdministratorMapper administratorMapper;

    private GeneralUtil generalUtil ;

    //------方法实现-------
    
    /**
    * @Description: 查询数据库表中所有administrator，封装成list
    * @Param: []
    * @return: java.util.List<com.example.demo.domain.Administrator>
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    @Override
    public List<Administrator> SelectAll() {
        return administratorMapper.selectAll();
    }

    /**
    * @Description: 使用account和password作为条件查询，返回查询到的对象,查找不到则回到登录页面
    * @Param: [account, password]
    * @return: org.springframework.web.servlet.ModelAndView
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    @Override
    public String  AdminLogin(@RequestParam String account,
                                   @RequestParam String password,
                                   Model model,
                                   RedirectAttributes redirectAttributes) {
        Administrator administrator = administratorMapper.Login(account,password);

        if (administrator == null){
            redirectAttributes.addFlashAttribute("errorInfo","用户名或者密码错误");
            return "redirect : /login";
        }
        //将密码设置为空，防止密码泄露
        administrator.setAdminPassword("");
        model.addAttribute("admin",administrator);
        return "admin/administratormain";
    }
    
    /**
    * @Description: 向Administrator表中新添加管理员用户，并进行判断是否越权
    * @Param: [tempAdmin]
    * @return: com.example.demo.domain.Administrator
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    @Override
    public Administrator AdminRegister(Administrator operator,String adminName,Integer adminSecureLevel,String adminPassword) {
        //发生越权授权，拒绝新建管理员
        if(operator.getAdminSecureLevel() > adminSecureLevel){
            //System.out.println("越权授权！");
            return null;
        }
        Administrator tempAdmin = new Administrator(adminName,adminPassword,adminSecureLevel);
        int res = administratorMapper.insert(tempAdmin);
        //插入失败，此处应该接受异常，后面再说吧
        if(res == 0){
            //System.out.println("插入数据库失败！");
            return null;
        }
        return administratorMapper.SelectAdminByName(tempAdmin.getAdminName());
    }

    /**
    * @Description: 按照管理员姓名查找管理员
    * @Param: [adminName]
    * @return: com.example.demo.domain.Administrator
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    @Override
    public Administrator SelectAdminByName(String adminName) {
        return administratorMapper.SelectAdminByName(adminName);
    }

    /**
    * @Description: 传入operator和target两个对象，前者操作删除后者
     *               在满足前者授权等级大于后者才可以操作
     *               最终返回操作结果
    * @Param: [operator, target]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    @Override
    public String DeleteAdministrator(Administrator operator, Administrator target) {
        String res = "";
        if(generalUtil.CheckAdminSecureLevel(operator,target)){
            //这里应该获取数据库操作异常
            administratorMapper.delete(target);
        }
        else res = "授权等级不符合，无法进行操作";
        return res;
    }

    /**
    * @Description: 接受传入的封装的Administrator对象，更新到表中
    * @Param: [administrator]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    @Override
    public String UpdateAdministrator(Administrator operator, Administrator target) {
        String res = "";
        if(generalUtil.CheckAdminSecureLevel(operator,target)){
            administratorMapper.updateByPrimaryKey(target);
            res = "修改成功！";
        }
        else res = "修改失败，请检查后重试！";
        return res;
    }


}
