package com.example.demo.service;

import com.example.demo.ViewObject.OrderPayWrapper;
import com.example.demo.ViewObject.SingleOrgOrderWrapper;
import com.example.demo.domain.OrgOrders;
import com.example.demo.domain.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public interface OrgOrderService {
    Long createOrder(SingleOrgOrderWrapper s, User u);

    int payOrder(Long userId, List<OrderPayWrapper> list, String payWay);

    PageInfo<OrderPayWrapper>  getOrderByStatus(Long userId, String orderStatus, int pageNum);

    String deleteOrders(Long userId,String[] arr);

    void userPayOrders(HttpSession session, Long userId, String[] arr);

    String updateOrdersStatus(Long userId, String[] arr,String status);

    String cancelOrders(Long userId, String[] arr);

    PageInfo<OrderPayWrapper> getAllOrders(Long userId,int pageNum);

    String getOrders(Long userId, String[] arr);
}
