package com.example.demo.mapper;

import com.example.demo.domain.Deposits;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.Date;

/**
 * @author ：chenjiajun
 * @description：TODO
 * @date ：2021/5/14 16:50
 */
@Component
@Mapper
public interface DepositsMapper extends BaseMapper<Deposits> {
    Deposits getOneByUserId(Long userId);

    void recharge(Long userId, String payWay, Date date,Integer cash);
}
