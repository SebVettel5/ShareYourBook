package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ：chenjiajun
 * @description：TODO
 * @date ：2021/5/6 22:48
 */
@Table(name = "address")
@Data
public class Address {
    @Id
    private Long addressId;
    private Long userId;
    private String addressInfo;
    private String addressRecipients;
    private Long addressPhone;


}
