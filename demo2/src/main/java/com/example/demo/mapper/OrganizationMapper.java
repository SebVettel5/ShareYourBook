package com.example.demo.mapper;


import com.example.demo.domain.Organization;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;


@Mapper
public interface OrganizationMapper extends BaseMapper<Organization> {
}
