package com.example.demo.util;

import com.example.demo.domain.Administrator;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ：chenjiajun
 * @description：常用工具类
 * @date ：2021/2/20 15:41
 */
public class GeneralUtil {
    
    /**
    * @Description: 比较操作对象和目标对象的授权等级，
    * @Param: [operator, target]
    * @return: boolean
    * @Author: chenjiajun
    * @Date: 2021/2/20
    */
    public boolean CheckAdminSecureLevel(Administrator operator,Administrator target){
        return operator.getAdminSecureLevel() > target.getAdminSecureLevel();
    }

    public String GetCode(){
        return "11111";
    }

//    public ModelAndView LoginToPage(Object object,String TargetPage,String ErrorPage){
//        ModelAndView mv = new ModelAndView();
//        if (object == null){
//            mv.addObject("error","账号或者密码错误，请检查后再登录");
//            mv.setViewName(ErrorPage);
//            return mv;
//        }
//        mv.addObject("user",u);
//        mv.setViewName(TargetPage);
//
//        return mv;
//    }
}
