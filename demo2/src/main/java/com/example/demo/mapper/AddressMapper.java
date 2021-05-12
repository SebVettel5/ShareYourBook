package com.example.demo.mapper;

import com.example.demo.domain.Address;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Component
public interface AddressMapper extends BaseMapper<Address> {

    List<Address> getAllByUserId(Long userId);
}
