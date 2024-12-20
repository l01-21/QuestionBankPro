package com.qbp.filter;

import com.qbp.constant.HttpStatus;
import com.qbp.model.entity.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 权限拦截器
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @javax.annotation.Resource
    private AntPathMatcher antPathMatcher;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取当前的路径
        String currentPath = request.getRequestURI();
        // 判断当前用户是否用于权限
        List<Resource> resources= (List<Resource>) request.getAttribute("resource");
        // 判断当前用户路径是否包含当前路径
        // 遍历资源列表，使用正则表达式匹配路径
        for (Resource resource : resources) {
            String regex = resource.getUrl();
            if (antPathMatcher.match(regex, currentPath)) {
                // 匹配成功，放行
                return true;
            }
        }
        response.setStatus(HttpStatus.FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("没有权限。");
        return false;
    }
}
