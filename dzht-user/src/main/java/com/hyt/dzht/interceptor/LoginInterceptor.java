package com.hyt.dzht.interceptor;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.hyt.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 忽略拦截的url
     */
    private String urls[] = {
            "/swagger-resources",
            "/webjars",
            "/v2",
            "/swagger-ui.html",
            "/doc.html",
            "/user/login",
            "/user/zhmmEmail",
            "/user/zhmm",
            "/user/img",
            "/user/sfz",
            "/user/zc",
            "/user/zcEmail",
            "/ht",
            "/downHt",
            "/error"
    };
    // 前置方法
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            Annotation methodAnnotation = hm.getMethodAnnotation(NoLogin.class);
            if(methodAnnotation!=null){
                return true;
            }
        }

        String url = request.getRequestURI();
        log.info("请求路径："+url);
        String token = request.getHeader(Constarnt.ACCESS_TOKEN);
        String method = request.getMethod();
        if (!method.equals("OPTIONS")){
            // 遍历需要忽略拦截的路径
            for (String item : this.urls){
                if (url.contains(item)){
                    log.info(url.indexOf(item)+" ");
                    return true;
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null ;
            // 查询验证token
            if (token == null){
                try{
                    R r = new R();
                    r.setMsg("请登录");
                    r.setCode(506);
                    String json =JSON.toJSONString(r);
                    response.setContentType("application/json");
                    out = response.getWriter();
                    // 返回json信息给前端
                    out.append(json);
                    out.flush();
                    return false;
                } catch (Exception e){
                    e.printStackTrace();
                    response.sendError(500);
                    return false;
                }
            }else if(JwtTokenUtil.isTokenExpired(token)){
                R r = new R();
                r.setMsg("身份认证已过期，重新登录");
                r.setCode(505);
                String json = JSON.toJSONString(r);
                response.setContentType("application/json");
                out = response.getWriter();
                // 返回json信息给前端
                out.append(json);
                out.flush();
                return false;
            }else{
                return true;
            }
        }else{
            return true;
        }
    }

    // 后置方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    // 完成时方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
