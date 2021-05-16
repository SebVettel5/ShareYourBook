package com.example.demo.controller;

import com.example.demo.ViewObject.OrderPayWrapper;
import com.example.demo.ViewObject.SingleBookOrderWrapper;
import com.example.demo.ViewObject.SingleOrgOrderWrapper;
import com.example.demo.domain.Deposits;
import com.example.demo.domain.Organization;
import com.example.demo.domain.User;
import com.example.demo.service.Impl.BookOrderServiceImpl;
import com.example.demo.service.Impl.DepositsServiceImpl;
import com.example.demo.service.Impl.OrgOrderServiceImpl;
import com.example.demo.util.GeneralUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author ：chenjiajun
 * @description：订单控制类
 * @date ：2021/5/13 1:48
 */
@Controller
public class OrderController {

    @Autowired
    private OrgOrderServiceImpl orgOrderServiceImpl;
    @Autowired
    private BookOrderServiceImpl bookOrderServiceImpl;
    @Autowired
    private DepositsServiceImpl depositsServiceImpl;

    /**
    * @Description: 获取订单确认信息
    * @Param: [model, session, totalSub, orderLocationId]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/13
    */
    @RequestMapping("/order/settleOrder")
    public String settleOrder(Model model, HttpSession session,Double totalSub){
        //获取支付信息
        List<OrderPayWrapper> list = (List<OrderPayWrapper>) session.getAttribute("payResult");
        //获取用户，验证押金支付情况
        User u = (User) session.getAttribute("user");
        Deposits deposits = depositsServiceImpl.getOneByUserId(u.getUserId());
        //如果没用缴纳押金，或者押金不足额，向支付信息添加一项
        if (null == deposits ||
                deposits.getDepositCash() <
                        GeneralUtil.UserDeposits){
            OrderPayWrapper temp = new OrderPayWrapper();
            temp.setOrgName("");
            temp.setOrderNo(new Long(0));
            temp.getBookAmountDaysInfo().add("押金支付");
            int sub = 0;
            if (null == deposits){
                sub = GeneralUtil.UserDeposits;
            }
            else{
                sub = GeneralUtil.UserDeposits - deposits.getDepositCash();
                totalSub += sub;
            }
            temp.setSubTotal(new Double(sub));
            session.setAttribute("deposit",false);
            //验证是否已经加入了押金验证
            if (!list.get(list.size()-1).getOrgName().equals("")){
                list.add(temp);
            }
        }else {
            session.setAttribute("deposit",true);
        }
        session.setAttribute("payResult",list);
        session.setAttribute("totalSub",totalSub);
        return "orderpay";
    }

    /**
    * @Description:前端在回传了总金额进行支付确认后，又回传了订单的书籍id、书籍数量序列、备注序列和地址
     * 依照这些信息、结合之前存在session中的订单包装类，可以进行按照机构的订单创建
     * 订单创建之前会有一个登录判断，未登录的话会被拦截器统一拦截到登录界面，同时丢失订购信息
    * @Param: [session, orderResult]
    * @return: void
    * @Author: chenjiajun
    * @Date: 2021/5/13
    */
    @RequestMapping("/order/buildOrderRecords")
    @ResponseBody
    public void buildOrderRecords(HttpSession session,@RequestBody List<String[]> orderResult){
        User u = (User) session.getAttribute("user");

        //返回付款信息，放入session中
        List<OrderPayWrapper> result = new ArrayList<>();
        //组织orderResult
        //其中orderResult[bookIds,bookDays,orgNotice,location]
        Map<Long,Integer> bookToDay = new HashMap<>();
        String[] bookIds = orderResult.get(0);
        String[] bookDays = orderResult.get(1);
        String[] orgNotices = orderResult.get(2);
        String[] orgTotals = orderResult.get(3);
        String[] locations = orderResult.get(4);
        //组装org订单指针;
        int orgOrderPoint = 0;
        //组装bookOrder指针
        int bookOrderPoint = 0;
        //获取到地址
        Long locationId = new Long(locations[0]);

        //确定书籍-数量关系
        for(int i = 0;i < bookIds.length;i++){
            bookToDay.put(new Long(bookIds[i]),new Integer(bookDays[i]));
        }
        //获取订单封装的map
        Map<SingleOrgOrderWrapper, List<SingleBookOrderWrapper>> temMap = (Map<SingleOrgOrderWrapper, List<SingleBookOrderWrapper>>) session.getAttribute("orderMap");
        //遍历map
        Iterator iterator = temMap.entrySet().iterator();
        while (iterator.hasNext()){
            //使用支付包装类获取信息
            OrderPayWrapper orderPayWrapper = new OrderPayWrapper();
            Map.Entry entry = (Map.Entry) iterator.next();
            //获取键(获取机构订单对象)
            SingleOrgOrderWrapper tempSigOrgOrder = (SingleOrgOrderWrapper)entry.getKey();
            //更新机构订单对象
            tempSigOrgOrder.updateData(locationId,orgTotals[orgOrderPoint],orgNotices[orgOrderPoint]);
            orgOrderPoint++;
            //向数据库新建机构订单,返回订单id
            Long orgOderId = orgOrderServiceImpl.createOrder(tempSigOrgOrder,u);
            //更新支付对象信息
            orderPayWrapper.setOrderNo(orgOderId);
            orderPayWrapper.setOrgName(tempSigOrgOrder.getOrgName());
            orderPayWrapper.setSubTotal(tempSigOrgOrder.getOrgCurSubTotal());
            //插入每一本书的订单
            //获取值
            List<SingleBookOrderWrapper> tempBookOrderList = (List<SingleBookOrderWrapper>)entry.getValue();
            //遍历list创建BookOrderRecords
            for (SingleBookOrderWrapper s:tempBookOrderList){
                //处理数据
                Long tempBookId = s.getBookId();
                //更新书籍包装类
                s.bookUpdate(bookToDay.get(tempBookId),orgOderId);
                //添加单个书籍订单记录
                bookOrderServiceImpl.createBookOrder(s,u,tempSigOrgOrder.getOrgId());
                orderPayWrapper.getBookAmountDaysInfo().add(s.getBookName()+"*"+s.getBookAmount()+"本*"+s.getBookDays()+"天");
            }
            //封装支付信息
            result.add(orderPayWrapper);
        }
        //清空session
        session.removeAttribute("orderMap");
        session.removeAttribute("singleBookOrderWrapper");
        //向Session中返回订单信息
        session.setAttribute("payResult",result);
    }

    @RequestMapping("/order/pay")
    public String orderPay(Model model,HttpSession session,String payWay){
        User u = (User) session.getAttribute("user");
        //获取支付信息
        List<OrderPayWrapper> list = (List<OrderPayWrapper>) session.getAttribute("payResult");

        //更新订单支付信息
        int curResult = orgOrderServiceImpl.payOrder(u.getUserId(),list,payWay);
        //更新押金标志
        boolean flag = list.get(list.size()-1).getOrgName().equals("");
        String message = "";

        if (flag){
            if (curResult == list.size()-1)
                message = "支付成功！";
            else
                message = "订单产生错误，支付失败！请稍后重试！";
        }
        else{
            if (curResult == list.size())
                message = "支付成功！";
            else
                message = "订单产生错误，支付失败！请稍后重试！";
        }
        model.addAttribute("payInfo",message);

        //清除session
        session.removeAttribute("totalSub");
        session.removeAttribute("payResult");
        session.removeAttribute("deposit");
        session.removeAttribute("locations");
        return "payresult";
    }


    /**
    * @Description: 用户按照状态获取订单
    * @Param: [model, session, pageNum, orderStatus]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/15
    */
    @RequestMapping("/user/getOrders")
    public String userOrderTest(Model model,HttpSession session,int pageNum,String orderStatus){
        User u = (User) session.getAttribute("user");
        PageInfo<OrderPayWrapper> list = orgOrderServiceImpl.getOrderByStatus(u.getUserId(),orderStatus,pageNum);
        model.addAttribute("orderResult",list);

        return "/user/"+orderStatus;
    }

    /**
    * @Description: 获取历史订单
    * @Param: [model, session, pageNum, orderStatus]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/getHistoryOrders")
    public String userGetHistoryOrders(Model model,HttpSession session,int pageNum){
        User u = (User) session.getAttribute("user");
        PageInfo<OrderPayWrapper> list = orgOrderServiceImpl.getAllOrders(u.getUserId(),pageNum);
        model.addAttribute("orderResult",list);
        return "/user/historyOrders";
    }

    /**
    * @Description: 删除订单
    * @Param: [session, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/deleteOrders")
    @ResponseBody
    public String uerOrderTest(HttpSession session,@RequestBody String[] arr){
        User u = (User) session.getAttribute("user");
        //批量更新
        String message = orgOrderServiceImpl.deleteOrders(u.getUserId(),arr);
        return message;
    }

    /**
    * @Description: 用户在个人中心选中要支付的订单，获取这些要支付的订单信息
    * @Param: [model, session, arr]
    * @return: void
    * @Author: chenjiajun
    * @Date: 2021/5/15
    */
    @RequestMapping("/user/payOrders")
    @ResponseBody
    public void userPayOrders(HttpSession session,@RequestBody String[] arr){
        User u = (User) session.getAttribute("user");
        orgOrderServiceImpl.userPayOrders(session,u.getUserId(),arr);
    }

    /**
    * @Description: 取消订单
    * @Param: [session, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/cancelOrders")
    @ResponseBody
    public String userCancelOrders(HttpSession session,@RequestBody String[] arr){
        User u = (User) session.getAttribute("user");
        //批量取消订单
        String message = orgOrderServiceImpl.cancelOrders(u.getUserId(),arr);
        return message;
    }

    /**
    * @Description: 确认收货
    * @Param: [session, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/confirmGetOrders")
    @ResponseBody
    public String userConfirmGetOrders(HttpSession session,@RequestBody String[] arr){
        User u = (User) session.getAttribute("user");
        //批量收货
        String message = orgOrderServiceImpl.getOrders(u.getUserId(),arr);
        return message;
    }

    /**
    * @Description: 弹窗返回归还页面
    * @Param: [model, bookOrderId]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/backBookOrder")
    public String userBackBookOrder(Model model,String bookOrderId){
        model.addAttribute("bookOrderId",bookOrderId);
        //获取商家的地址信息
        Organization org = bookOrderServiceImpl.getOrg(new Long(bookOrderId));
        model.addAttribute("org",org);
        return "user/backBook";
    }

    /**
    * @Description: 执行书籍订单归还
    * @Param: [model, bookOrderId, postName, postNo]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @RequestMapping("/user/bookBackPost")
    public String userBookBackPost(Model model,Long bookOrderId,String postName,String postNo){
        model.addAttribute("bookOrderId",bookOrderId);
        //获取商家的地址信息
        Organization org = bookOrderServiceImpl.getOrg(bookOrderId);
        model.addAttribute("org",org);
        //更新归还物流
        model.addAttribute("optionMessage", bookOrderServiceImpl.updateBackPost(postName,postNo,bookOrderId));
        return "user/backBook";
    }


}
