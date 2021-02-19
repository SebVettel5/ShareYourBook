package com.example.demo.mapper;

import com.example.demo.domain.Administrator;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

@Mapper
public interface AdministratorMapper extends BaseMapper<Administrator> {
}
