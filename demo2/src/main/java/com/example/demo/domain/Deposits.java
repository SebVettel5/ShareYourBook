package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @author ：chenjiajun
 * @description：押金实体类
 * @date ：2021/5/14 16:42
 */
@Data
@Table(name = "deposits")
public class Deposits {
    private Long depositId;
    private Date depositDatetiem;
    private Long depositUserId;
    private String depositType;
    private Integer depositCash;
    private Date depositBackTime;
    private Boolean depositIsBack;

    public Deposits(Date depositDatetiem, Long depositUserId, String depositType, Integer depositCash) {
        this.depositDatetiem = depositDatetiem;
        this.depositUserId = depositUserId;
        this.depositType = depositType;
        this.depositCash = depositCash;
    }
}
