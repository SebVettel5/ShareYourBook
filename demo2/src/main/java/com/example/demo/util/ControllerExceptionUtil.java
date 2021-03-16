package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：chenjiajun
 * @description：统一的异常处理类
 * @date ：2021/3/8 23:34
 */
@ControllerAdvice
//对所有Controller进行拦截
public class ControllerExceptionUtil {
    //获取日志对象
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    //表示该方法可作为异常处理
    public ModelAndView ExceptionHandler(HttpServletRequest request,Exception e) throws Exception{
        //控制台输出异常
        logger.error("Request URL : {}, Exception : {}",request.getRequestURL(),e);

//        System.out.println(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) );
        //判断异常有没有指定状态，指定了的交由SpringBoot自行处理，没有的统一跳转到通用异常处理页面
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        //跳转到通用异常界面
        ModelAndView mv = new ModelAndView();
        //参数准备
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");

        return mv;
    }

}
