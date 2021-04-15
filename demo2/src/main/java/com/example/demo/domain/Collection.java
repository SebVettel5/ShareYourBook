package com.example.demo.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：chenjiajun
 * @description：书籍收藏记录实体类
 * @date ：2021/3/24 20:21
 */
@Table(name="collections")
public class Collection {

    @Id
    private Long collectId;
    private Date collectCreatetime;
    private Long collectUserId;
    private String collectUsernickname;
    private Long collectBookId;
    private String collectBookName;
    private String collectBookPic;
    private Long collectOrgId;
    private String collectOrgName;
    private String collectBookAuthor;
    private Long collectBookAmount;
    private Long collectBookMoney;

    public Collection() {
    }

    public Collection(Long collectId, Date collectCreatetime, Long collectUserId, String collectUsernickname, Long collectBookId, String collectBookName, String collectBookPic, Long collectOrgId, String collectOrgName, String collectBookAuthor, Long collectBookAmount, Long collectBookMoney) {
        this.collectId = collectId;
        this.collectCreatetime = collectCreatetime;
        this.collectUserId = collectUserId;
        this.collectUsernickname = collectUsernickname;
        this.collectBookId = collectBookId;
        this.collectBookName = collectBookName;
        this.collectBookPic = collectBookPic;
        this.collectOrgId = collectOrgId;
        this.collectOrgName = collectOrgName;
        this.collectBookAuthor = collectBookAuthor;
        this.collectBookAmount = collectBookAmount;
        this.collectBookMoney = collectBookMoney;
    }

    public Long getCollectId() {
        return collectId;
    }

    public void setCollectId(Long collectId) {
        this.collectId = collectId;
    }

    public Date getCollectCreatetime() {
        return collectCreatetime;
    }

    public void setCollectCreatetime(Date collectCreatetime) {
        this.collectCreatetime = collectCreatetime;
    }

    public Long getCollectUserId() {
        return collectUserId;
    }

    public void setCollectUserId(Long collectUserId) {
        this.collectUserId = collectUserId;
    }

    public String getCollectUsernickname() {
        return collectUsernickname;
    }

    public void setCollectUsernickname(String collectUsernickname) {
        this.collectUsernickname = collectUsernickname;
    }

    public Long getCollectBookId() {
        return collectBookId;
    }

    public void setCollectBookId(Long collectBookId) {
        this.collectBookId = collectBookId;
    }

    public String getCollectBookName() {
        return collectBookName;
    }

    public void setCollectBookName(String collectBookName) {
        this.collectBookName = collectBookName;
    }

    public Long getCollectOrgId() {
        return collectOrgId;
    }

    public void setCollectOrgId(Long collectOrgId) {
        this.collectOrgId = collectOrgId;
    }

    public String getCollectOrgName() {
        return collectOrgName;
    }

    public void setCollectOrgName(String collectOrgName) {
        this.collectOrgName = collectOrgName;
    }

    public String getCollectBookAuthor() {
        return collectBookAuthor;
    }

    public void setCollectBookAuthor(String collectBookAuthor) {
        this.collectBookAuthor = collectBookAuthor;
    }

    public String getCollectBookPic() {
        return collectBookPic;
    }

    public void setCollectBookPic(String collectBookPic) {
        this.collectBookPic = collectBookPic;
    }

    public Long getCollectBookAmount() {
        return collectBookAmount;
    }

    public void setCollectBookAmount(Long collectBookAmount) {
        this.collectBookAmount = collectBookAmount;
    }

    public Long getCollectBookMoney() {
        return collectBookMoney;
    }

    public void setCollectBookMoney(Long collectMoney) {
        this.collectBookMoney = collectMoney;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "collectId=" + collectId +
                ", collectCreatetime=" + collectCreatetime +
                ", collectUserId=" + collectUserId +
                ", collectUsernickname='" + collectUsernickname + '\'' +
                ", collectBookId=" + collectBookId +
                ", collectBookName='" + collectBookName + '\'' +
                ", collectBookPic='" + collectBookPic + '\'' +
                ", collectOrgId=" + collectOrgId +
                ", collectOrgName='" + collectOrgName + '\'' +
                ", collectBookAuthor='" + collectBookAuthor + '\'' +
                ", collectAmount=" + collectBookAmount +
                ", collectMoney=" + collectBookMoney +
                '}';
    }
}
