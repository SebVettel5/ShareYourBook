package com.example.demo.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：User实体类
 * @date ：2021/2/1 16:13
 */
@Table(name="users")
public class User {

    @Id
    private Long userId;
    private String userNickname;
    private Long userPhone;
    private String userPassword;
    private String userEmail;
    private String userAvatar;
    private int userAge;
    private String userSex;

    //注册时需要的构造方法


    public User(String userNickname, Long userPhone, String userEmail, String userPassword) {
        this.userNickname = userNickname;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }

    //登陆时使用手机号和密码的构造方法
    public User(Long userPhone, String userPassword) {
        this.userPhone = userPhone;
        this.userPassword = userPassword;
    }

    //全参构造方法
    public User(Long userId, String userNickname, Long userPhone, String userPassword, String userEmail, String userAvatar, int userAge, String userSex) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userPhone = userPhone;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userAvatar = userAvatar;
        this.userAge = userAge;
        this.userSex = userSex;
    }

    public User() {

    }

    public User(String s1, Long aLong, String s2) {
        this.setUserNickname(s1);
        this.setUserPhone(aLong);
        this.setUserPassword(s2);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userNickname='" + userNickname + '\'' +
                ", userPhone=" + userPhone +
                ", userPassword='" + userPassword + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userAge=" + userAge +
                ", userSex='" + userSex + '\'' +
                '}';
    }
}
