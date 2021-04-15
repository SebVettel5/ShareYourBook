package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：chenjiajun
 * @description：图书借阅订单实体类
 * @date ：2021/3/27 18:04
 */
@Data
@Table(name = "bookborrowforms")
public class BorrowForms {
    @Id
    private Long borId;
    private Date borCreateTime;
    private Long borUserId;
    private String borUserNickname;
    private Long borOrgId;
    private String borOrgName;
    private Date borGetTime;//读者获取到书籍的时间
    private Date borReturnTime;//读者寄回的时间
    private Long borPrice;//订单金额
    private String borBookName;
    private String borBookPic;
    private Long borCount;
    private String borStatus;

    public BorrowForms(Long borId, Date borCreateTime, Long borUserId, String borUserNickname, Long borOrgId, String borOrgName, Date borGetTime, Date borReturnTime, Long borPrice, String borBookName, String borBookPic, Long borCount, String borStatus) {
        this.borId = borId;
        this.borCreateTime = borCreateTime;
        this.borUserId = borUserId;
        this.borUserNickname = borUserNickname;
        this.borOrgId = borOrgId;
        this.borOrgName = borOrgName;
        this.borGetTime = borGetTime;
        this.borReturnTime = borReturnTime;
        this.borPrice = borPrice;
        this.borBookName = borBookName;
        this.borBookPic = borBookPic;
        this.borCount = borCount;
        this.borStatus = borStatus;
    }
}
