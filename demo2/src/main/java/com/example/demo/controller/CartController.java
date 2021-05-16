package com.example.demo.controller;

import com.example.demo.ViewObject.CartWrapper;
import com.example.demo.domain.Book;
import com.example.demo.domain.Cart;
import com.example.demo.domain.User;
import com.example.demo.service.Impl.BookServiceImpl;
import com.example.demo.service.Impl.CartServiceImpl;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author ：chenjiajun
 * @description：收藏控制器
 * @date ：2021/5/16 15:03
 */
@Controller
public class CartController {
    @Autowired
    private CartServiceImpl cartServiceImpl;
    @Autowired
    private BookServiceImpl bookServiceImpl;

    /**
    * @Description: 加入购物车
     * 对于登录用户，直接返回一条确认信息
     * 对于未登录用户，则返回到登录页面，登录后再跳转到当前页面
    * @Param: [model, session, bookId]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/putInCart")
    public String putInCart(Model model, HttpSession session,String bookId,String booksAmount,String booksDays){
        User u = (User) session.getAttribute("user");
        Book book = bookServiceImpl.getBook(new Long(bookId));
        if (null == u){
            //如果用户不存在，返回到详情界面，进行文字提示
                if (null != book){
                    model.addAttribute("optionMessage","未登录，加入购物车失败，请稍后重试！");
                    model.addAttribute("book",book);
                } else{
                    return "error/404";
                }
        }else {
            //用户存在,加入购物车
            Cart cart = new Cart(bookId,u.getUserId(),book.getBookOrgId(),booksAmount,booksDays);
            int result = cartServiceImpl.insert(cart);
            if (result == 1)
                model.addAttribute("optionMessage","加入购物车成功！可在用户中心查看。");
            else
                model.addAttribute("optionMessage","加入购物车失败，请稍后重试！");
        }
        model.addAttribute("book",book);
        return "/bookInfo";
    }

    /**
    * @Description: 分页获取购物车
    * @Param: [model, session, pageNum]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/getCart")
    public String userGetCart(Model model,HttpSession session,int pageNum){
        User u = (User) session.getAttribute("user");
        PageInfo<CartWrapper>  cart = cartServiceImpl.getUserCart(u.getUserId(),pageNum);
        System.out.println(cart);
        model.addAttribute("cartResult",cart);
        return "user/collection";
    }

    /**
    * @Description: 取消购物车
    * @Param: [session, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/cancelCart")
    @ResponseBody
    public String userCancelCart(HttpSession session, @RequestBody String[] arr){
        User u = (User) session.getAttribute("user");
        return cartServiceImpl.removeCarts(arr,u.getUserId());
    }
    /**购物车内对书籍参数进行改变
    * @Description:
    * @Param: [session, arr]
    * @return: void
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/updateCart")
    @ResponseBody
    public void userUpdateCart(HttpSession session,@RequestBody String[] arr){
        //arr[carId,amount,days,postage]
        Cart cartTemp = new Cart(new Long(arr[0]),Integer.valueOf(arr[1]),Integer.valueOf(arr[2]),new Double(arr[3]));
        User u = (User) session.getAttribute("user");
        cartServiceImpl.updateCart(u.getUserId(),cartTemp);
    }

    /**
    * @Description: 获取要进行支付的订单并进行订单确认
    * @Param: [session, arr]
    * @return: void
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/goPay")
    @ResponseBody
    public void userGoPay(HttpSession session,@RequestBody String[] arr){
        
    }
}
