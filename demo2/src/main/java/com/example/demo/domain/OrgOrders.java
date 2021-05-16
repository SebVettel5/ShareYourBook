package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：chenjiajun
 * @description：机构订单实体类
 * 机构订单存放的是同一次在一家机构借阅的书籍的订单信息
 * @date ：2021/5/13 1:33
 */
@Data
@Table(name = "orgorders")
public class OrgOrders {
    @Id
    private Long orgOrderId;
    private Long orgOrderOrgId;
    private String orgOrderOrgName;
    private Long orgOrderUserId;
    private String orgOrderUserName;
    private Date orgOrderCreateTime;
    private Double orgOrderSub;
    private String orgOrderPayWay;
    private String orgOrderStatus;
    private String orgOrderPostId;
    private String orgOrderPostName;
    private Date orgOrderSendTime;
    private Boolean orgOrderDataRecordStatus;
    private Long orgOrderAddressId;
    private Date orgOrderUserGetTime;
    private Integer orgOrderBookOrderAmounts;

    public OrgOrders(Long orgId, String orgName, Long userId, String userNickname, Date date, Double orgCurSubTotal, String orderStatus, Long locationId) {
        this.orgOrderId = orgId;
        this.orgOrderOrgName = orgName;
        this.orgOrderUserId = userId;
        this.orgOrderUserName = userNickname;
        this.orgOrderCreateTime = date;
        this.orgOrderSub = orgCurSubTotal;
        this.orgOrderStatus = orderStatus;
        this.orgOrderAddressId = locationId;
    }
}
