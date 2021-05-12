package com.example.demo.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ：chenjiajun
 * @description：TODO
 * @date ：2021/3/20 18:04
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
//    登录拦截部分
    @Autowired
    private LoginInterceptor loginInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//            registry.addInterceptor(loginInterceptor)
//            //此处添加需要拦截的路径，为便于开发，暂时不进行相关操作
//                    .addPathPatterns("/personalpage")
//                    .addPathPatterns("/org/uploadbookpage")
////                    .addPathPatterns("/org/*")
//                    ;
//    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //这里的opt是我D盘下面的opt目录，在配置文件配置过，图片存储的位置
        //域名例如：127.0.0.1:8080/opt/1.jpg
        registry.addResourceHandler("/localImg/**").addResourceLocations("file:E:/localImg/");
        registry.addResourceHandler("org/static/css/images/**").addResourceLocations("classpath:/static/css/images/");
        registry.addResourceHandler("/shareBookPic/bookCover/**").addResourceLocations("file:E:/shareBookPic/bookCover/");
    }
}
