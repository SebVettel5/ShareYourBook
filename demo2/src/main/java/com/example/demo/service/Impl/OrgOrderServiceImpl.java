package com.example.demo.service.Impl;

import com.example.demo.ViewObject.OrderPayWrapper;
import com.example.demo.ViewObject.SingleBookOrderWrapper;
import com.example.demo.ViewObject.SingleOrgOrderWrapper;
import com.example.demo.domain.*;
import com.example.demo.mapper.*;
import com.example.demo.service.OrgOrderService;
import com.example.demo.util.GeneralUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：chenjiajun
 * @description：机构订单服务实现类
 * @date ：2021/5/13 1:52
 */
@Service
public class OrgOrderServiceImpl implements OrgOrderService {
    @Autowired
    private OrgOrderMapper orgOrderMapper;
    @Autowired
    private BookOrderMapper bookOrderMapper;
    @Autowired
    private DepositsMapper depositsMapper;
    @Autowired
    private BooksMapper booksMapper;
    @Autowired
    private AddressMapper addressMapper;

    Map<String,String> orderStatusMap = new HashMap<String, String>(){{
       put("waitPay","未支付");
       put("havePay","已支付");
       put("onTrans","运输中");
       put("reading","阅读中");
       put("overTime","已超时");
    }};

    /**
    * @Description: 插入一条机构订单记录，并返回这条记录的对象
    * @Param: [s, u]
    * @return: com.example.demo.domain.OrgOrders
    * @Author: chenjiajun
    * @Date: 2021/5/13
    */
    @Override
    public Long createOrder(SingleOrgOrderWrapper s, User u) {
        OrgOrders orgOrders = new OrgOrders(s.getOrgId(),s.getOrgName(),u.getUserId(),u.getUserNickname(),new Date(),s.getOrgCurSubTotal(),"未支付",s.getLocationId());
       orgOrderMapper.insertOne(orgOrders);
        return orgOrders.getOrgOrderId();
    }

    /**
    * @Description: 支付订单后更新数据库
    * @Param: [userId, list, payWay]
    * @return: void
    * @Author: chenjiajun
    * @Date: 2021/5/14
    */
    @Override
    public int payOrder(Long userId, List<OrderPayWrapper> list, String payWay) {
        //如果存在对押金的缴纳
        if (list.get(list.size()-1).getOrgName().equals("")){
            Deposits deposits  = depositsMapper.getOneByUserId(userId);
            if (deposits != null)
                //更新
                depositsMapper.recharge(userId,payWay,new Date(), GeneralUtil.UserDeposits);
            else{
                //新建
                deposits = new Deposits(new Date(),userId,payWay, GeneralUtil.UserDeposits);
                depositsMapper.insert(deposits);
            }
        }
        int result = 0;
        //处理订单
        for (OrderPayWrapper orderPayWrapper:list) {
            result += orgOrderMapper.afterPay(orderPayWrapper.getOrderNo(),payWay,"已支付");
            //更新订单中书籍数量，便于后面做订单状态更新
            orgOrderMapper.updateOrderBookAmount(orderPayWrapper.getOrderNo());
        }

        return result;
    }

    /**
    * @Description: 分页查询机构订单信息，并将每个机构订单信息的子订单信息查询到，进行图片路径更新
    * @Param: [userId, orderStatus, pageNum]
    * @return: com.github.pagehelper.PageInfo<java.util.List<com.example.demo.ViewObject.OrderPayWrapper>>
    * @Author: chenjiajun
    * @Date: 2021/5/15
    */
    @Override
    public PageInfo<OrderPayWrapper>  getOrderByStatus(Long userId, String orderStatus, int pageNum) {
        //进行分页查询(startPage只对最近的查询有影响)
        PageHelper.startPage(pageNum,5);
        List<OrderPayWrapper> result = new ArrayList<>();
        //获取全部的未支付的机构订单
        List<OrgOrders> orgOrdersList = orgOrderMapper.getAllByIdAndStatus(userId,orderStatusMap.get(orderStatus) ,true);
        //遍历查询到的未支付订单，并查询其书籍子订单，封装到结果中
        for (OrgOrders orgOrders:orgOrdersList) {
            //将机构订单进行封装
            OrderPayWrapper tempOrder = new OrderPayWrapper(orgOrders);
            //获取所有子订单
            List<BookOrders> bookOrdersList = bookOrderMapper.getAllByIdAndStatus(userId,tempOrder.getOrderNo(),true);
            //处理书籍图片路径
            bookOrdersList = GeneralUtil. bookOrderDataPre(bookOrdersList);
            //更新剩余天数
            if (orderStatus.equals("reading")){
                for (BookOrders b:bookOrdersList){
                    Date now = new Date();
                    Date g = orgOrders.getOrgOrderUserGetTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                        int day = (int) ((now.getTime() - g.getTime()) / (1000 * 60 * 60 * 24)); // 计算天
                        b.setBookOrderRemindDays(b.getBookOrderBookDays()-day);
                }
            }

            tempOrder.setBookList(bookOrdersList);
            //处理收件信息
            tempOrder.setAddress(addressMapper.selectByPrimaryKey(orgOrders.getOrgOrderAddressId()));

            result.add(tempOrder);
        }
        PageInfo<OrderPayWrapper> pageInfo = new  PageInfo<>(result);
        System.out.println(pageInfo);
        return pageInfo;
    }

    /**
    * @Description: 用户订单记录
    * @Param: [userId, arr]
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/5/15
    */
    @Override
    public String deleteOrders(Long userId,String[] arr) {
        List<Long> l = new ArrayList<>();
        for (String s:arr) {
            l.add(new Long(s));
        }
        int curResult = l.size();
        int result = orgOrderMapper.deleteOrders(l,userId);
            return "共选中"+curResult+"条数据，成功删除"+result+"条!";
    }

    @Override
    public void userPayOrders(HttpSession session,Long userId, String[] arr) {
        List<Long> orderIdList = new ArrayList<>();
        //当前支付总金额
        Double totalSub = 0.00;
        for (String s:arr) {
            orderIdList.add(new Long(s));
        }
        //按照订单id获取所有订单
        //返回的待支付list
        List<OrderPayWrapper> orderPayWrappers = new ArrayList<>();
        //获取当前用户的待支付订单
        List<OrgOrders> list = orgOrderMapper.getOrdersByList(orderIdList,"未支付",userId);
        //将orgOrders封装到orderPayWarpper中
        for(OrgOrders o:list){
            totalSub += o.getOrgOrderSub();
            orderPayWrappers.add(new OrderPayWrapper(o));
        }
        //将orgOrder的子订单情况获取到
        //押金处理
        //获取押金信息
        Deposits deposits = depositsMapper.getOneByUserId(userId);
        if (null == deposits || (deposits.getDepositCash() < GeneralUtil.UserDeposits)){
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
            if (!orderPayWrappers.get(list.size()-1).getOrgName().equals("")){
                orderPayWrappers.add(temp);
            }
        }else {
            session.setAttribute("deposit",true);
        }
        session.setAttribute("payResult",orderPayWrappers);
        session.setAttribute("totalSub",totalSub);
    }

    @Override
    public String updateOrdersStatus(Long userId, String[] arr, String status) {
        List<Long> l = new ArrayList<>();
        for (String s:arr) {

            l.add(new Long(s));
        }
        int curResult = l.size();
        int result = orgOrderMapper.updateOrdersStatus(l,userId,status);
        return "共选中"+curResult+"条订单，成功操作"+result+"条!";
    }

    @Override
    public String cancelOrders(Long userId, String[] arr) {
        List<Long> l = new ArrayList<>();
        for (String s:arr) {

            l.add(new Long(s));
        }
        int curResult = l.size();
        int result = orgOrderMapper.cancelOrders(l,userId);
        return "共选中"+curResult+"条订单，成功取消"+result+"条!";
    }

    @Override
    public PageInfo<OrderPayWrapper> getAllOrders(Long userId, int pageNum) {
        //进行分页查询(startPage只对最近的查询有影响)
        PageHelper.startPage(pageNum,5);
        List<OrderPayWrapper> result = new ArrayList<>();
        //获取已完成的机构订单
        List<OrgOrders> orgOrdersList = orgOrderMapper.getAllOrdersById(userId,true);
        //遍历查询到订单，并查询其书籍子订单，封装到结果中
        for (OrgOrders orgOrders:orgOrdersList) {
            //将机构订单进行封装
            OrderPayWrapper tempOrder = new OrderPayWrapper(orgOrders);
            //获取所有子订单
            List<BookOrders> bookOrdersList = bookOrderMapper.getAllByIdAndStatus(userId,tempOrder.getOrderNo(),true);
            //处理书籍图片路径
            bookOrdersList = GeneralUtil. bookOrderDataPre(bookOrdersList);
            tempOrder.setBookList(bookOrdersList);
            //处理收件信息
            tempOrder.setAddress(addressMapper.selectByPrimaryKey(orgOrders.getOrgOrderAddressId()));
            result.add(tempOrder);
        }
        PageInfo<OrderPayWrapper> pageInfo = new  PageInfo<>(result);
        System.out.println(pageInfo);
        return pageInfo;
    }

    @Override
    public String getOrders(Long userId, String[] arr) {
        List<Long> list = new ArrayList<>();
        for (String s:arr) {
            list.add(new Long(s));
        }
        int curResult = list.size();
        Date date = new Date();
        //更新ogrOrder订单状态和收货时间
        int result = orgOrderMapper.getConfirmOrders(list,userId,date);
        //更新bookOrders收货时间
        bookOrderMapper.getConfirmOrders(list,userId,date);
        return "共选中"+curResult+"条订单，成功收货"+result+"条!";
    }

}
