package com.example.demo.controller;

import com.example.demo.ViewObject.OrdersWrapper;
import com.example.demo.ViewObject.SingleBookOrderWrapper;
import com.example.demo.ViewObject.SingleOrgOrderWrapper;
import com.example.demo.domain.Book;
import com.example.demo.domain.Cart;
import com.example.demo.domain.Organization;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.Impl.*;
import org.apache.velocity.util.ArrayListWrapper;
import org.springframework.ui.Model;
import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：chenjiajun
 * @description：涉及用户的操作控制映射
 * @date ：2021/2/24 16:34
 */
@Controller
public class UserController {
    //Spring自动封装bean
    //user服务实现类
    @Autowired
    private UserServiceImpl userServiceImpl ;
    @Autowired
    private BookServiceImpl bookServiceImpl;
    @Autowired
    private AddressServiceImpl addressServiceImpl;
    @Autowired
    private OrganizationServiceImpl organizationServiceImpl;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CartServiceImpl cartServiceImpl;

    @RequestMapping("/insertTest")
    @ResponseBody
    public String insertTest(){
        User u = new User("测试",new Long(12345678),"测试");
        userMapper.insert(u);
        return u.getUserId().toString();

    }

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
    public int DeleteUserById(Long id){
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
     * @return: org.springframework.web.servlet.ModelAndView
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @PostMapping("/UserLogin")
    public String UserLogin(@RequestParam String account,
                            @RequestParam String password,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            HttpSession session){
            return userServiceImpl.UserLogin(account,password,model,redirectAttributes,session);
    }


    /**
    * @Description: 确认订单登录
     * 在立即购买时，若发现没有登录，则进行登录并在登录完成后进行订单信息解析和创建订单
    * @Param: [account, password, model, session]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/10
    */
    @PostMapping("/buyLoginConfirm")
    public String buyLoginConfirm(String account,
                            String password,
                            Model model,
                            HttpSession session){
        User u = userServiceImpl.login(account,password);
        SingleBookOrderWrapper  singleBookOrderWrapper;
        if (null == u){
            //登录失败，返回登录页面
            model.addAttribute("type","transfer");
            model.addAttribute("errorInfo","用户名或者密码错误");
            return "login";
        }
        //从Session中获取到单本书籍订单包装类
        singleBookOrderWrapper = (SingleBookOrderWrapper) session.getAttribute("singleBookOrderWrapper");
        //开始数据处理
        List<SingleBookOrderWrapper> list = new ArrayList<>();
        list.add(singleBookOrderWrapper);
        //按照机构归类获取书籍,返回给前端一个封装好了的结果集
        Map<SingleOrgOrderWrapper,List<SingleBookOrderWrapper>> resultBookMap = getBooksByOrg(list);
        //获取总计
        Double orderSub = resultBookMap.get(null).get(0).getCurSubTotal();
        //去除临时放置总计的包装类
        resultBookMap.remove(null);
        //-------完成书籍获取、封装
        //用户地址获取、封装
        session.setAttribute("locations",addressServiceImpl.getAllAddressByUser(u.getUserId()));
        //返回书籍map
//        model.addAttribute("orderMap",resultBookMap);
        session.setAttribute("orderMap",resultBookMap);
        //返回总计
        model.addAttribute("orderSub",orderSub);
        session.setAttribute("user",u);
        session.setAttribute("userType","user");
        return "/buyConfirm";
    }

    /**
     * @Description: 用户初次注册时，通过手机号和密码直接注册
     * @Param: [userphone, userpassword]
     * @return: com.example.demo.domain.User
     * @Author: chenjiajun
     * @Date: 2021/2/19
     */
    @RequestMapping("/UserRegister")
    public String UserRegister(Long phone,String username,String password,String email,
                               Model model,RedirectAttributes redirectAttributes,
                               HttpSession session){
        return  userServiceImpl.UserRegister(phone,username,password,email,model,redirectAttributes,session);
    }

    /**
     * @Description: 当用户在书籍页面点击立即借阅时，如果用户未登录，将跳转到登录界面，并且将携带的书籍id,数量进行传递到login页面
     * 登录后，依赖登录请求的种类进行分情况登录，对付款确认类型的登录，登陆后直接跳转到付款确认界面
     * @Param: [model, session, bookId, booksAmount]
     * @return: java.lang.String
     * @Author: chenjiajun
     * @Date: 2021/5/7
     */
    @RequestMapping("/buyBookNow")
    public String buyBookNow(Model model,HttpSession session,Long bookId,int booksAmount,int booksDays){
        User u = (User) session.getAttribute("user");
        session.removeAttribute("orderMap");
        session.removeAttribute("singleBookOrderWrapper");
        //如果用户没有登录，先跳转到登录界面，然后再进入到订单确认界面
        SingleBookOrderWrapper  singleBookOrderWrapper;
        if(null == u){
            //将订单信息封装到但本书籍订单中中
            singleBookOrderWrapper = new SingleBookOrderWrapper(bookId,booksAmount,booksDays);
            //将图书订单信息存入Session中
            session.setAttribute("singleBookOrderWrapper",singleBookOrderWrapper);
            model.addAttribute("type","transfer");
            return "login";
        }
        //从Session中获取到单本书籍订单包装类
        singleBookOrderWrapper = (SingleBookOrderWrapper) session.getAttribute("singleBookOrderWrapper");
        //当用户在登录的情况下再次下单时，为了防止出现读取到上一个session值得问题，之前的session被清除
        if (singleBookOrderWrapper == null){
            singleBookOrderWrapper = new SingleBookOrderWrapper(bookId,booksAmount,booksDays);
        }
        //开始数据处理
        List<SingleBookOrderWrapper> list = new ArrayList<>();
        list.add(singleBookOrderWrapper);
        //按照机构归类获取书籍,返回给前端一个封装好了的结果集
        Map<SingleOrgOrderWrapper,List<SingleBookOrderWrapper>> resultBookMap = getBooksByOrg(list);
        //获取总计
        Double orderSub = resultBookMap.get(null).get(0).getCurSubTotal();
        //去除临时放置总计的包装类
        resultBookMap.remove(null);
        //-------完成书籍获取、封装
        //用户地址获取、封装
        session.setAttribute("locations",addressServiceImpl.getAllAddressByUser(u.getUserId()));
        //返回书籍map
//        model.addAttribute("orderMap",resultBookMap);
        session.setAttribute("orderMap",resultBookMap);
        //返回总计
        model.addAttribute("orderSub",orderSub);
        return "/buyConfirm";
    }


    @RequestMapping("/user/goPayCart")
    @ResponseBody
    public void userGoPay(HttpSession session,@RequestBody String[] arr){
        User u =(User) session.getAttribute("user");
        List<SingleBookOrderWrapper> list = new ArrayList<>();
        //获取所有的购物车内选中的订单
        list = cartServiceImpl.getUserCartList(u.getUserId(),arr,list);
        //将获取到的购物车订单组成SingleBookWrapper
//        System.out.println("---------------------------------------------------------------------------------------------");
//        System.out.println(list);
        Map<SingleOrgOrderWrapper,List<SingleBookOrderWrapper>> resultBookMap = getBooksByOrg(list);
//        System.out.println(resultBookMap);
        session.setAttribute("orderMap",resultBookMap);
        //用户地址获取、封装
        session.setAttribute("locations",addressServiceImpl.getAllAddressByUser(u.getUserId()));
    }



    //工具方法

    /**
     * @Description: 按照订单封装类进行书籍按机构处理，并对书籍和机构合法性进行相关验证
     * @Param: [ordersWrapper]
     * @return: java.util.Map<java.lang.String,java.util.Map<com.example.demo.domain.Book,java.lang.Integer>>
     * @Author: chenjiajun
     * @Date: 2021/5/11
     */
    public  Map<SingleOrgOrderWrapper,List<SingleBookOrderWrapper>> getBooksByOrg(List<SingleBookOrderWrapper> list) {
        Map<SingleOrgOrderWrapper,List<SingleBookOrderWrapper>> resultBookMap = new HashMap<>();
        Double total = new Double(0);
        //orgName - SingleOrgOrderWrapper
        Map<String,SingleOrgOrderWrapper> tempMap = new HashMap<>();
        //遍历list
        for (SingleBookOrderWrapper s:list) {
            //获取书籍
            Book book = bookServiceImpl.getBook(s.getBookId());
            if (null != book){
                //获取、验证店铺合法性
                Organization org = organizationServiceImpl.checkLegality(book.getBookOrgId());
                if (null != org){
                    //进行数据封装
                    SingleOrgOrderWrapper tempSoo =
                            tempMap.getOrDefault(org.getOrgName(),
                                    new SingleOrgOrderWrapper(org.getOrgName(),org.getOrgId(),0.00));
                    tempSoo.setOrgId(org.getOrgId());
                    List<SingleBookOrderWrapper> l = resultBookMap.getOrDefault(tempSoo,new ArrayList<SingleBookOrderWrapper>());
                    //更新单本书籍订单信息
                    s.preValue(book);
                    l.add(s);
                    //更新total
                    total += s.getCurSubTotal();
                    //更新机构订单总计
                    tempSoo.setOrgCurSubTotal(tempSoo.getOrgCurSubTotal()+s.getCurSubTotal());
                    resultBookMap.put(tempSoo,l);
                }
            }
        }
        //放入总计
        List<SingleBookOrderWrapper> tempList = new ArrayList<>();
        SingleBookOrderWrapper singleBookOrderWrapper = new SingleBookOrderWrapper(total);
        tempList.add(singleBookOrderWrapper);
        resultBookMap.put(null,tempList);
        return resultBookMap;
    }
}
