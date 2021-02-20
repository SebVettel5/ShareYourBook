package com.example.demo.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：管理员实体类
 * @date ：2021/2/1 16:32
 */
@Table(name = "administrator")
public class   Administrator {
    private Long AdminId;//主键
    private String AdminName;
    private String AdminPassword;
    private Integer AdminSecureLevel;
    private String AdminEmail;

    public Administrator(Long adminId, String adminName, String adminPassword, Integer adminSecureLevel, String adminEmail) {
        AdminId = adminId;
        AdminName = adminName;
        AdminPassword = adminPassword;
        AdminSecureLevel = adminSecureLevel;
        AdminEmail = adminEmail;
    }

    public Administrator(String adminName, String adminPassword, Integer adminSecureLevel) {
        AdminName = adminName;
        AdminPassword = adminPassword;
        AdminSecureLevel = adminSecureLevel;
    }

    public void setAdminId(Long adminId) {
        AdminId = adminId;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public void setAdminPassword(String adminPassword) {
        AdminPassword = adminPassword;
    }

    public void setAdminSecureLevel(Integer adminSecureLevel) {
        AdminSecureLevel = adminSecureLevel;
    }

    public void setAdminEmail(String adminEmail) {
        AdminEmail = adminEmail;
    }

    public Long getAdminId() {
        return AdminId;
    }

    public String getAdminName() {
        return AdminName;
    }

    public String getAdminPassword() {
        return AdminPassword;
    }

    public Integer getAdminSecureLevel() {
        return AdminSecureLevel;
    }

    public String getAdminEmail() {
        return AdminEmail;
    }
}
