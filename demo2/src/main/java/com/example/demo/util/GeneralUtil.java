package com.example.demo.util;

import com.example.demo.domain.Administrator;

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
}
