package com.qbp.filter;

import com.qbp.constant.HttpStatus;
import com.qbp.context.UserContext;
import com.qbp.model.vo.LoginVO;
import com.qbp.service.impl.TokenService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LoginVO loginUser =  tokenService.getLoginUser(request);
        if (loginUser == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("请进行登录后操作。。。");
            return false;
        }
        request.setAttribute("resource", loginUser.getResources());
        UserContext.setCurrentUserId(loginUser.getId());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
