package com.example.demo.mapper;

import com.example.demo.domain.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

@Mapper
@Component
public interface CartMapper extends BaseMapper<Cart> {
    int deleteUSerCart(Long cartId, Long userId);

    List<Cart> getCartList(Long userId, String[] arr);
}
