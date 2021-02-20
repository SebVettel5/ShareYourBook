package com.example.demo.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：User实体类
 * @date ：2021/2/1 16:13
 */
@Table(name="user")
public class User {
    @Id
    private String UserId;
    private String UserName;
    @Id                     //用户登录时按照UserPhone为主键查找用户
    private String UserPhone;
    private String UserEmail;
    private String UserHeadPic;//用户头像路径
    private String UserLevel;
    private String UserPassword;
    private String UserAge;
    private String UserSex;
    private String UserCareer;
    private String UserOrg;//用户所属机构

    public User() {
    }

    public User(String userId, String userName, String userPhone, String userEmail, String userHeadPic, String userLevel, String userPassword, String userAge, String userSex, String userCareer, String userOrg) {
        UserId = userId;
        UserName = userName;
        UserPhone = userPhone;
        UserEmail = userEmail;
        UserHeadPic = userHeadPic;
        UserLevel = userLevel;
        UserPassword = userPassword;
        UserAge = userAge;
        UserSex = userSex;
        UserCareer = userCareer;
        UserOrg = userOrg;
    }
    
    /**
    * @Description: 使用userphone和userpassword两个属性的构造方法，适用于用户初次注册场景
    * @Param: [userPhone, userPassword]
    * @return:
    * @Author: chenjiajun
    * @Date: 2021/2/19
    */
    public User(String userPhone, String userPassword) {
        UserPhone = userPhone;
        UserPassword = userPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserId='" + UserId + '\'' +
                ", UserName='" + UserName + '\'' +
                ", UserPhone='" + UserPhone + '\'' +
                ", UserEmail='" + UserEmail + '\'' +
                ", UserHeadPic='" + UserHeadPic + '\'' +
                ", UserLevel='" + UserLevel + '\'' +
                ", UserPassword='" + UserPassword + '\'' +
                ", UserAge='" + UserAge + '\'' +
                ", UserSex='" + UserSex + '\'' +
                ", UserCareer='" + UserCareer + '\'' +
                ", UserOrg='" + UserOrg + '\'' +
                '}';
    }

    public String getUserAge() {
        return UserAge;
    }

    public String getUserSex() {
        return UserSex;
    }

    public String getUserCareer() {
        return UserCareer;
    }

    public String getUserOrg() {
        return UserOrg;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String username) {
        UserName = username;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserHeadPic() {
        return UserHeadPic;
    }

    public void setUserHeadPic(String userHeadPic) {
        UserHeadPic = userHeadPic;
    }

    public String getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(String userLevel) {
        UserLevel = userLevel;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
