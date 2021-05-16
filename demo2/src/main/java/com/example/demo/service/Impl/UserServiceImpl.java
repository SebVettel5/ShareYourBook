package com.example.demo.service.Impl;

import com.example.demo.domain.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：用户服务实现类
 * @date ：2021/2/1 17:18
 */
@Service
@Transactional
@SessionAttributes(value = {"user","errorInfo"})
public class UserServiceImpl implements UserService {
    @Resource
    //Resource 自动ByName进行注解
    private UserMapper userMapper;

    //实现查询全部用户
    @Override
    public List<User> SelectAllUser() {
        return userMapper.selectAll();
    }

    //按UserPhone查询用户
    @Override
    public User SelectUserByPhone(Long Phone) {
        return userMapper.selectByPrimaryKey(Phone);
    }

    //添加用户
    @Override
    public String  UserRegister(@RequestParam Long phone,
                                @RequestParam String username,
                                @RequestParam String userpassword,
                                String email,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                HttpSession session) {
        User u = new User(username,phone,email,userpassword);
        //先验证是否存在该用户
        if( userMapper.SelectUserByPhone(u.getUserPhone()) != null){
            redirectAttributes.addFlashAttribute("errorInfo","注册失败，该用户已经存在，请联系管理员");
            return "redirect:/login";
        }
        //插入失败，捕获异常，进行异常处理，重定向到login
        try{
            userMapper.NewUserRegister(u);
        }
        catch (DuplicateKeyException e){
            redirectAttributes.addFlashAttribute("errorInfo","注册失败，该用户已经存在，请联系管理员");
            return "redirect:/login";
        }

        //插入成功，直接跳转到readercommunity,并返回查询到的user对象
        u = userMapper.SelectUserByName(u.getUserNickname());
        u.setUserPassword("");
        session.setAttribute("user",u);
        session.setAttribute("userType","user");
//        model.addAttribute("user",u);
        return "redirect:/readercommunity";
    }

    //按id删除用户
    @Override
    public int DeleteUserById(Long id) {
        User u = new User();
        u.setUserId(id);
        return userMapper.delete(u);
    }

    //更新用户信息
    @Override
    public int UpdateUserInformation(User u) {
        int res = userMapper.updateByPrimaryKeySelective(u);
        return res;
    }
    /**
    * @Description: 用户登录，在数据库中按照密码账户查找用户，找到的跳转到用户界面，找不到的跳转到当前界面，返回登录错误
    * @Param: [account, password]
    * @return: org.springframework.web.servlet.ModelAndView
    * @Author: chenjiajun
    * @Date: 2021/3/11
    */
    @Override
    public String UserLogin(String phone,
                            @RequestParam String password,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {
        User u = userMapper.Login(Long.valueOf(phone),password);

        //当没有查询到用户，返回错误代码
        if(u == null){
            redirectAttributes.addFlashAttribute("errorInfo","用户名或者密码错误");
//            model.addAttribute("errorInfo","用户名或者密码错误");
            return "redirect:login";
        }
        u.setUserPassword("");
        //将用户对象和用户类型传入会话域中
        session.setAttribute("user",u);

        //这里使用userType来区分用户类别
        session.setAttribute("userType","user");
        return "redirect:/readercommunity";
    }

    /**
    * @Description: 登录
    * @Param: [account, password]
    * @return: com.example.demo.domain.User
    * @Author: chenjiajun
    * @Date: 2021/5/7
    */
    @Override
    public User login(String account, String password) {
       return userMapper.selectOne(new User(new Long(account),password));
    }


}
