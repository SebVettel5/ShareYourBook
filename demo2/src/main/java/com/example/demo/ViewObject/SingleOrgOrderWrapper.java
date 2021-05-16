package com.example.demo.ViewObject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    private String orgNotice;
    private Long locationId;

    public SingleOrgOrderWrapper(String orgName, Long orgId, Double orgCurSubTotal) {
        this.orgName = orgName;
        this.orgId = orgId;
        this.orgCurSubTotal = orgCurSubTotal;
    }

    /**
    * @Description: 更新机构订单包装类
    * @Param: [locations, orgTotal, orgNotice]
    * @return: void
    * @Author: chenjiajun
    * @Date: 2021/5/13
    */
    public void updateData(Long locations, String orgTotal, String orgNotice) {
        this.orgNotice = orgNotice;
        this.orgCurSubTotal = new Double(orgTotal);
        this.locationId = locations;
    }
}
