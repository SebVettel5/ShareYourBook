package com.example.demo.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：机构实体类
 * @date ：2021/2/1 16:35
 */
@Table(name="organization")
public class Organization {
    @Id
    private String OrganizationId;
    private String OrganizationName;
    private String OrganizationPassword;
    private String OrganizationEmail;
    private String OrganizationHeadPic;//头像路径
    private Integer OrganizationLevel;

    //全参构造函数
    public Organization(String organizationId, String organizationName, String organizationPassword, String organizationEmail, String organizationHeadPic, Integer organizationLevel) {
        OrganizationId = organizationId;
        OrganizationName = organizationName;
        OrganizationPassword = organizationPassword;
        OrganizationEmail = organizationEmail;
        OrganizationHeadPic = organizationHeadPic;
        OrganizationLevel = organizationLevel;
    }

    //除id,等级外的构造函数，用于添加的时候临时构建对象
    public Organization(String organizationName, String organizationPassword, String organizationEmail, String organizationHeadPic) {
        OrganizationName = organizationName;
        OrganizationPassword = organizationPassword;
        OrganizationEmail = organizationEmail;
        OrganizationHeadPic = organizationHeadPic;
    }

    public String getOrganizationId() {
        return OrganizationId;
    }

    public void setOrganizationId(String organizationId) {
        OrganizationId = organizationId;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(String organizationName) {
        OrganizationName = organizationName;
    }

    public String getOrganizationPassword() {
        return OrganizationPassword;
    }

    public void setOrganizationPassword(String organizationPassword) {
        OrganizationPassword = organizationPassword;
    }

    public String getOrganizationEmail() {
        return OrganizationEmail;
    }

    public void setOrganizationEmail(String organizationEmail) {
        OrganizationEmail = organizationEmail;
    }

    public String getOrganizationHeadPic() {
        return OrganizationHeadPic;
    }

    public void setOrganizationHeadPic(String organizationHeadPic) {
        OrganizationHeadPic = organizationHeadPic;
    }

    public Integer getOrganizationLevel() {
        return OrganizationLevel;
    }

    public void setOrganizationLevel(Integer organizationLevel) {
        OrganizationLevel = organizationLevel;
    }
}
