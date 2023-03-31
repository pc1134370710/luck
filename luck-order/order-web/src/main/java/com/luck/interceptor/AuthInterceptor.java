package com.luck.interceptor;

import com.luck.annotation.NotNeedLogin;
import com.luck.constant.CommonEnum;
import com.luck.constant.Constant;
import com.luck.exception.GlobalException;
import com.luck.model.UserAuth;
import com.luck.resp.R;
import com.luck.utils.RedisUtils;
import com.luck.utils.UserInfoThreadLocal;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @description:  另一种实现方式 是继承 HandlerInterceptorAdapter适配器
 * @author: pangcheng
 * @time: 2023/2/2 9:35
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 请求方法之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put("cguid", UUID.randomUUID().toString());
        /*
            SpringBoot 拦截器在拦截请求时需要判断是否是 Controller 处理器，因为不是所有的请求都会被 Controller 处理器处理。
            而判断是否是 Controller 处理器可以通过判断 handler 是否是 HandlerMethod 实例来完成。
            HandlerMethod 是 SpringMVC 框架中的一个类，代表具体的处理方法，包含方法所属对象、所属类、方法名、方法参数和方法注解等信息
            ，可以用于反射调用等操作。而其他的请求处理器，如静态资源文件处理器、404错误处理器等并不是 HandlerMethod 的实例，
            因此可以通过这个方式来判断 handler 是否为 Controller 处理器。",
         */
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        NotNeedLogin methodAnnotation = handlerMethod.getMethodAnnotation(NotNeedLogin.class);
        if(methodAnnotation !=null){
            return true;
        }
        String token = request.getHeader(Constant.ACCESS_TOKEN);
        if(StringUtils.isEmpty(token)){
            // 未登录
            throw new GlobalException(R.ERROR(CommonEnum.LOGIN_FAIL));
        }
        UserAuth userAuth = (UserAuth) redisUtils.get(token);
        UserInfoThreadLocal.set(userAuth);

        return true;
    }

    /**
     * 请求处理完毕后 调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoThreadLocal.remove();
        response.setHeader("cguid",MDC.get("cguid"));
        MDC.clear();
    }
}
