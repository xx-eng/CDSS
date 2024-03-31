package com.config;

/**
 * @author xueli
 * @email 2632624281@qq.com
 * @date 2021-06-07 23:08
 * @description
 */
/**
 * 允许跨域请求
 * @author Administrator
 *
 */
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import javax.servlet.http.*;
//@Component
//public class FilterConfig implements HandlerInterceptor{
//
//    @Override
//    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
//            throws Exception {
//    }
//
//    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2)
//            throws Exception {
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
//
//        response.setHeader("Access-Control-Allow-Origin",request.getHeader("Origin"));//支持跨域请求
//        response.setHeader("Access-Control-Allow-Methods", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");//是否支持cookie跨域
//        response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept,Access-Token");//Origin, X-Requested-With, Content-Type, Accept,Access-Token
//        return true;
//    }
//}