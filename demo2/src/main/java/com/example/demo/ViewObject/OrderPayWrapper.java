package com.example.demo.ViewObject;

import com.example.demo.domain.Address;
import com.example.demo.domain.BookOrders;
import com.example.demo.domain.OrgOrders;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：支付信息包装类
 * @date ：2021/5/14 16:27
 */
@Data
public class OrderPayWrapper {
    String orgName;
    //在支付页面简要说明支付项目的list
    List<String> bookAmountDaysInfo = new ArrayList<>();
    Long orderNo;
    Double subTotal;
    Long orgId;
    //在个人页面显示单条书籍订单的list
    List<BookOrders> bookList = new ArrayList<>();
    private Address address;
    private OrgOrders orgOrders;

    public OrderPayWrapper(OrgOrders orgOrders) {
        this.orgName = orgOrders.getOrgOrderOrgName();
        this.orderNo = orgOrders.getOrgOrderId();
        this.subTotal = orgOrders.getOrgOrderSub();
        this.orgId = orgOrders.getOrgOrderOrgId();
        this.orgOrders = orgOrders;
    }

    public OrderPayWrapper() {
    }
}
