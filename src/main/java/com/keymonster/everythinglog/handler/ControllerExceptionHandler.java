package com.keymonster.everythinglog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * created by yangjie sheting on 2020/10/24
 *异常处理类
 * /
/*
* annotation  注解
*
*
* */
@ControllerAdvice
public class ControllerExceptionHandler  {
    private final Logger logger=LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public  ModelAndView exceptionHandler(HttpServletRequest request, Exception e)throws Exception {
        logger.error("请求的路径：{},异常：{}",request.getRequestURI(),e);
        if (AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class)!=null){
            throw e;
        }
        ModelAndView modelandview =new ModelAndView();
        modelandview.addObject("url",request.getRequestURI());
        modelandview.addObject("exception",e);
        modelandview.setViewName("error/error");
        return modelandview;

     }
}
