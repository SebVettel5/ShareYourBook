package com.example.demo.ViewObject;

import lombok.Data;

/**
 * @author ：chenjiajun
 * @description：订单确认机构包装类
 * @date ：2021/5/12 17:37
 */
@Data
public class SingleOrgOrderWrapper {
    private String orgName;
    private Long orgId;
    private Double orgCurSubTotal;

    public SingleOrgOrderWrapper(String orgName, Long orgId, Double orgCurSubTotal) {
        this.orgName = orgName;
        this.orgId = orgId;
        this.orgCurSubTotal = orgCurSubTotal;
    }
}
