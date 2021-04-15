package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：机构实体类
 * @date ：2021/2/1 16:35
 */
@Data
@Table(name="organizations")
public class Organization {
  @Id
  private Long orgId;
  private String orgName;
  private String orgPassword;
  private String orgEmail;
  private String orgAvatar;
  private Long orgAuthenticationId;
  private Long orgFansCount;

    public Organization(Long orgId, String orgName, String orgPassword, String orgEmail, String orgAvatar, Long orgAuthenticationId, Long orgFansCount) {
        this.orgId = orgId;
        this.orgName = orgName;
        this.orgPassword = orgPassword;
        this.orgEmail = orgEmail;
        this.orgAvatar = orgAvatar;
        this.orgAuthenticationId = orgAuthenticationId;
        this.orgFansCount = orgFansCount;
    }
}
