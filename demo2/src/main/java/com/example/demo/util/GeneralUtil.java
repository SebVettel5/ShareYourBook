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

    /**
    * @Description: 生成随机数方法
    * @Param: []
    * @return: java.lang.String
    * @Author: chenjiajun
    * @Date: 2021/3/17
    */
    public String GetCheckCode(){
        int ran = (int) ((Math.random() * 9 + 1) * Math.pow(10,  4));
        System.out.println(ran);
        return String.valueOf(ran);
    }
}
