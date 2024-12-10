package com.qbp.service.impl;

import com.qbp.constant.CacheConstants;
import com.qbp.constant.Constants;
import com.qbp.model.entity.User;
import com.qbp.model.vo.LoginVO;
import com.qbp.utils.RedisCacheUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 */
@Component
public class TokenService {
    // 令牌自定义标识
    @Getter
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Getter
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认7天）
    @Getter
    @Value("${token.expireTime}")
    private Long expireTime;

    @Resource
    private RedisCacheUtils redisCacheUtils;

    /**
     * 创建令牌
     *
     * @param user 用户信息
     * @return 令牌
     */
    public String createToken(LoginVO user) {
        String token = UUID.randomUUID().toString();
        refreshToken(user, token);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return createToken(claims);
    }

    /**
     * 刷新令牌有效期
     *
     * @param user  登录信息
     * @param token token
     */
    public void refreshToken(LoginVO user, String token) {
        redisCacheUtils.setCacheObject(CacheConstants.LOGIN_TOKEN_KEY + token, user, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 获取用户身份信息
     *
     * @param request HttpServletRequest
     * @return 用户信息
     */
    public LoginVO getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                return redisCacheUtils.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY + uuid);
            } catch (Exception e) {

            }
        }
        return null;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取请求token
     *
     * @param request HttpServletRequest
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        return token;
    }
}
