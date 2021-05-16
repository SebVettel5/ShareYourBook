package com.example.demo.mapper;

import com.example.demo.domain.OrgOrders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface OrgOrderMapper extends BaseMapper<OrgOrders> {
    int deleteOrders(List<Long> l,Long userId);

    Long insertOne(OrgOrders orgOrders);

    int afterPay(Long orderNo, String payWay, String orderStatus);

    List<OrgOrders> getAllByIdAndStatus(Long userId, String orderStatus,Boolean dataStatus);

    List<OrgOrders> getOrdersByList(List<Long> orderIdList,String status,Long userId);

    int updateOrdersStatus(List<Long> l, Long userId,String status);

    int cancelOrders(List<Long> l, Long userId);

    List<OrgOrders> getAllOrdersById(Long userId, boolean dataStatus);

    int getConfirmOrders(List<Long> list, Long userId, Date date);

    void updateOrderBookAmount(Long orderNo);
}
