package com.example.demo.service.Impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.example.demo.ViewObject.CartWrapper;
import com.example.demo.ViewObject.SingleBookOrderWrapper;
import com.example.demo.domain.Book;
import com.example.demo.domain.Cart;
import com.example.demo.mapper.BooksMapper;
import com.example.demo.mapper.CartMapper;
import com.example.demo.service.CartService;
import com.example.demo.util.GeneralUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：chenjiajun
 * @description：购物车服务实现类
 * @date ：2021/5/16 15:04
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private BooksMapper booksMapper;

    /**
    * @Description: 实现加入购物车的功能
    * @Param: [cart]
    * @return: int
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @Override
    public int insert(Cart cart) {
        //先查看当前用户的购物车中是否有这本书
        Cart selectCart = new Cart(cart);
        Cart cartTemp = cartMapper.selectOne(selectCart);
        if (null == cartTemp)
                return cartMapper.insert(cart);
        else{
            //更新信息
            cart.setCartId(cartTemp.getCartId());
            cart.setCartBookAmount(cartTemp.getCartBookAmount()+cart.getCartBookAmount());
            cart.setCartBookDays(cartTemp.getCartBookDays()+cart.getCartBookDays());
            if (cart.getCartBookDays() > 30)cart.setCartPostage(0.00);
            return cartMapper.updateByPrimaryKeySelective(cart);
        }
    }

    /**
    * @Description: 分页获取
    * @Param: [userId, pageNum]
    * @return: com.github.pagehelper.PageInfo<com.example.demo.ViewObject.CartWrapper>
    * @Author: chenjiajun
    * @Date: 2021/5/16
    */
    @Override
    public PageInfo<CartWrapper> getUserCart(Long userId, int pageNum) {
        //先获取用户下所有cart
        PageHelper.startPage(pageNum,10);
        List<Cart> cartList = cartMapper.select(new Cart(userId));
        List<CartWrapper> cartWrappers = new ArrayList<>();
        for (Cart c:cartList) {
            //按照获取到的购物车id进行获取书籍信息，整合到视图包装类中
            Book tempBook = booksMapper.selectByPrimaryKey(c.getCartBookId());
            CartWrapper cartWrapper = new CartWrapper();
            if (tempBook != null){
                //整合图片
                tempBook = GeneralUtil.preBookCover(tempBook,GeneralUtil.localBooksCoverLocation);
                cartWrapper.setBook(tempBook);
                cartWrapper.setCartItem(c);
                cartWrappers.add(cartWrapper);
            }
        }

        return new PageInfo<>(cartWrappers);
    }

    @Override
    public String removeCarts(String[] arr,Long userId) {
        int result = 0;
        for (String s:arr){
            result += cartMapper.deleteUSerCart(new Long(s),userId);
        }
        return "共选中"+arr.length+"本，成功取消"+result+"本";
    }

    @Override
    public void updateCart(Long userId, Cart cartTemp) {
        cartTemp.setCartUserId(userId);
        cartMapper.updateByPrimaryKeySelective(cartTemp);
    }

    @Override
    public List<SingleBookOrderWrapper> getUserCartList(Long userId, String[] arr, List<SingleBookOrderWrapper> list) {
        //获取所有cart
        List<Cart> l = cartMapper.getCartList(userId,arr);
        //遍历获取的结果，获取书籍，然后转化成singleBookOrderWrapper
        for (Cart c:l) {
            SingleBookOrderWrapper singleBookOrderWrapper;
            Book book = booksMapper.selectByPrimaryKey(c.getCartBookId());
            //书籍图片地址转化
            book = GeneralUtil.preBookCover(book,GeneralUtil.localBooksCoverLocation);
            //更新sing的信息
            singleBookOrderWrapper = new SingleBookOrderWrapper(c);
            singleBookOrderWrapper.preValue(book);
            list.add(singleBookOrderWrapper);
        }
        return  list;
    }
}
