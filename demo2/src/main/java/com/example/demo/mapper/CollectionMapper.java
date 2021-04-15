package com.example.demo.mapper;

import com.example.demo.domain.Collection;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface CollectionMapper extends BaseMapper<Collection> {
    List<Collection> selectByUserId(Long userid);
}
