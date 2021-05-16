package com.example.demo.service.Impl;

import com.example.demo.ViewObject.SingleBookOrderWrapper;
import com.example.demo.domain.BookOrders;
import com.example.demo.domain.OrgOrders;
import com.example.demo.domain.Organization;
import com.example.demo.domain.User;
import com.example.demo.mapper.BookOrderMapper;
import com.example.demo.mapper.OrgOrderMapper;
import com.example.demo.mapper.OrganizationMapper;
import com.example.demo.service.BookOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ：chenjiajun
 * @description：TODO
 * @date ：2021/5/13 1:53
 */
@Service
public class BookOrderServiceImpl implements BookOrderService {
    @Autowired
    private BookOrderMapper bookOrderMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private OrgOrderMapper orgOrderMapper;

    /**
    * @Description: 插入一条新的书籍订单
    * @Param: [s, u, orgId]
    * @return: void
    * @Author: chenjiajun
    * @Date: 2021/5/13
    */
    @Override
    public void createBookOrder(SingleBookOrderWrapper s, User u,Long orgId) {
        //书籍图片去路径的处理
        String[] strSplit = s.getBookPic().split("/");
        String bookPic = strSplit[strSplit.length-1];
        BookOrders bookOrders = new BookOrders(s.getOrgOrderId(),new Date(),u.getUserId(),orgId,s.getBookId(),s.getBookName(),bookPic,s.getBookPrice(),s.getBookDays(),s.getBookAmount(),s.getBookPostage(),s.getBookPublisher(),s.getBookAuthor(),s.getBookEdition());
        bookOrderMapper.insert(bookOrders);
    }

    @Override
    public Organization getOrg(Long bookOrderId) {
        BookOrders b = bookOrderMapper.selectByPrimaryKey(bookOrderId);
        System.out.println(bookOrderId);
        return organizationMapper.selectByPrimaryKey(b.getBookOrderOrgId());
    }

    @Override
    public String updateBackPost(String postName, String postNo,Long bookOrderId) {
        if (1 == bookOrderMapper.updateBackInfo(postName,postNo,bookOrderId,new Date())){
            //更新机构订单
            OrgOrders orgOrders = orgOrderMapper.selectByPrimaryKey(bookOrderMapper.selectByPrimaryKey(bookOrderId).getBookOrderOrgOrderId());
            orgOrders.setOrgOrderBookOrderAmounts(orgOrders.getOrgOrderBookOrderAmounts()-1);
            if (orgOrders.getOrgOrderBookOrderAmounts() == 0){
                orgOrders.setOrgOrderStatus("已完成");
            }
            orgOrderMapper.updateByPrimaryKey(orgOrders);
            return "归还成功！";
        }
        else {
            return "归还失败！";
        }
    }
}
