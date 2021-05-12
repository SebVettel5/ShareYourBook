package com.example.demo.controller;

import com.example.demo.ViewObject.OrdersWrapper;
import com.example.demo.ViewObject.SingleBookOrderWrapper;
import com.example.demo.ViewObject.SingleOrgOrderWrapper;
import com.example.demo.domain.Book;
import com.example.demo.domain.Organization;
import com.example.demo.service.Impl.AddressServiceImpl;
import com.example.demo.service.Impl.BookServiceImpl;
import com.example.demo.service.Impl.OrganizationServiceImpl;
import org.springframework.ui.Model;
import com.example.demo.domain.User;
import com.example.demo.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
        if (null == u){
            //登录失败，返回登录页面
            model.addAttribute("type","transfer");
            model.addAttribute("errorInfo","用户名或者密码错误");
            return "login";
        }
        //从Session中获取到书籍订单包装类
        OrdersWrapper ordersWrapper = (OrdersWrapper) session.getAttribute("tempOrderWrapper");
//        System.out.println(ordersWrapper);
        //开始数据处理
        //按照机构归类获取书籍
        Map<String,Map<Book,Integer>> resultBookMap = getBooksByOrg(ordersWrapper);
        //-------完成书籍获取、封装
        //用户地址获取、封装
        model.addAttribute("locations",addressServiceImpl.getAllAddressByUser(u.getUserId()));
        //返回书籍map
        model.addAttribute("bookMap",resultBookMap);
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
        //如果用户没有登录，先跳转到登录界面，然后再进入到订单确认界面

        if(null == u){
            //将订单信息封装到map中
            OrdersWrapper ordersWrapper = new OrdersWrapper();
            ordersWrapper.getBookIdSet().add(bookId);
            ordersWrapper.getBookAmount().put(bookId,booksAmount);
            //将图书订单信息存入Session中
            session.setAttribute("tempOrderWrapper",ordersWrapper);
            model.addAttribute("type","transfer");
            return "login";
        }
        //从Session中获取到书籍订单包装类
        OrdersWrapper ordersWrapper = (OrdersWrapper) session.getAttribute("tempOrderWrapper");
        //开始数据处理
        //按照机构归类获取书籍
        Map<String, Map<Book,Integer>> resultBookMap = getBooksByOrg(ordersWrapper);
        //-------完成书籍获取、封装
        //用户地址获取、封装
        model.addAttribute("locations",addressServiceImpl.getAllAddressByUser(u.getUserId()));
        //返回书籍map
        model.addAttribute("bookMap",resultBookMap);
        return "/buyConfirm";
    }



    //工具方法

    /**
     * @Description: 按照订单封装类进行书籍按机构处理，并对书籍和机构合法性进行相关验证
     * @Param: [ordersWrapper]
     * @return: java.util.Map<java.lang.String,java.util.Map<com.example.demo.domain.Book,java.lang.Integer>>
     * @Author: chenjiajun
     * @Date: 2021/5/11
     */
    public  Map<String, Map<Book, Integer>> getBooksByOrg(OrdersWrapper ordersWrapper) {
        //-----开始获取、封装书籍
        //获取Map
        Map<SingleOrgOrderWrapper, SingleBookOrderWrapper> tempBookMap = ordersWrapper.getBookAmount();
        //使用map往model传输<机构,<书籍-数量>>对应信息
        Map<String,Map<Book,Integer>> resultBookMap = new HashMap<>();
        //遍历tempBookMapper获取书籍放入bookMap中
        for (Long bookId: tempBookMap.keySet()) {
            Book book = bookServiceImpl.getOderBook(bookId);
            if (null != book){
                int bookAmount = tempBookMap.get(bookId);
                //获取、验证店铺合法性
                Organization org = organizationServiceImpl.checkLegality(book.getBookOrgId());
                if (null != org){
                    //先获取resultBookMap中orgName对应的书籍-数量键值对Map
                    Map<Book,Integer> bookMountTemp = resultBookMap.getOrDefault(org.getOrgName(),new HashMap<Book,Integer>(){{
                        put(book,bookAmount);
                    }});
                    //将书籍-数量键值对放到获取到的bookMountTemp中
                    bookMountTemp.put(book,bookAmount);
                    //将获取到的机构-<书籍-数量>键值对放到resultBookMap中
                    resultBookMap.put(org.getOrgName(),bookMountTemp);
                }
            }
        }
        return resultBookMap;
    }
}
