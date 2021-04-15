package com.example.demo.interceptor;

import com.example.demo.domain.Administrator;
import com.example.demo.domain.Organization;
import com.example.demo.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author ：chenjiajun
 * @description：登录拦截器
 * @date ：2021/3/20 17:57
 */
//@Component
//注入到spring容器中
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        try{
            HttpSession session = request.getSession();
            User u = (User) session.getAttribute("user");
            Administrator administrator = (Administrator) session.getAttribute("admin");
            Organization organization = (Organization) session.getAttribute("organization");
            //没有任何用户类型，则跳转到登录界面
            if ( u == null && administrator == null && organization == null){
                response.sendRedirect("login");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //return true表示当前条件符合，不进行拦截，false进行拦截
        return true;
    }
}
