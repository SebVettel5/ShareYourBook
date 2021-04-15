package com.example.demo.mapper;

import com.example.demo.domain.BorrowForms;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface BorrowFormsMapper extends BaseMapper<BorrowForms> {
}
