package com.example.demo.domain;

import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：管理员实体类
 * @date ：2021/2/1 16:32
 */
@Table(name = "administrator")
public class   Administrator {
    private String Adminid;
    private String Adminname;
    private String Adminpassword;
    private Integer Adminsecurelevel;
    private String Adminemail;

    @Override
    public String toString() {
        return "Administrator{" +
                "Adminid='" + Adminid + '\'' +
                ", Adminname='" + Adminname + '\'' +
                ", Adminpassword='" + Adminpassword + '\'' +
                ", Adminsecurelevel=" + Adminsecurelevel +
                ", Adminemail='" + Adminemail + '\'' +
                '}';
    }

    public String getAdminname() {
        return Adminname;
    }

    public void setAdminname(String adminname) {
        Adminname = adminname;
    }

    public String getAdminpassword() {
        return Adminpassword;
    }

    public void setAdminpassword(String adminpassword) {
        Adminpassword = adminpassword;
    }

    public Integer getAdminsecurelevel() {
        return Adminsecurelevel;
    }

    public void setAdminsecurelevel(Integer adminsecurelevel) {
        Adminsecurelevel = adminsecurelevel;
    }

    public String getAdminemail() {
        return Adminemail;
    }

    public void setAdminemail(String adminemail) {
        Adminemail = adminemail;
    }

    public String getAdminid() {
        return Adminid;
    }

    public void setAdminid(String adminid) {
        Adminid = adminid;
    }
}
