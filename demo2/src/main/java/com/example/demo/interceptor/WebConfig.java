package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：chenjiajun
 * @description：TODO
 * @date ：2021/3/20 18:04
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(loginInterceptor);
            //此处添加需要拦截的路径，为便于开发，暂时不进行相关操作
//                    .addPathPatterns("/personalpage")
//                    .addPathPatterns("/uploadbook")
//                    ;
    }
}
