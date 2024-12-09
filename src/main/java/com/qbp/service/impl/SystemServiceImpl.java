package com.qbp.service.impl;

import com.qbp.constant.CacheConstants;
import com.qbp.constant.UserConstants;
import com.qbp.exception.Asserts;
import com.qbp.model.dto.RegisterDTO;
import com.qbp.model.entity.User;
import com.qbp.service.SystemService;
import com.qbp.service.UserService;
import com.qbp.utils.RedisCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统服务实现类
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Resource
    private RedisCacheUtils redisCacheUtils;
    @Resource
    private UserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public String register(RegisterDTO registerDTO) {
        String msg = "", username = registerDTO.getUsername(), password = registerDTO.getPassword();
        User user = new User();
        user.setUsername(username);
        // 校验验证码
        validateCaptcha(registerDTO.getCode(), registerDTO.getUuid());
        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "用户名长度必须在" + UserConstants.USERNAME_MIN_LENGTH + "-" + UserConstants.USERNAME_MAX_LENGTH + "之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在" + UserConstants.PASSWORD_MIN_LENGTH + "-" + UserConstants.PASSWORD_MAX_LENGTH + "之间";
        } else if (!userService.checkUserNameUnique(user)) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else {
            user.setNickname(username);
            user.setPassword(passwordEncoder.encode(password));
            boolean result = userService.save(user);
            if (!result) {
                msg = "注册失败，请联系系统管理人员";
            }
        }
        return msg;
    }


    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     */
    private void validateCaptcha(String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String cacheObject = redisCacheUtils.getCacheObject(verifyKey);
        redisCacheUtils.deleteObject(verifyKey);
        if (cacheObject == null) {
            Asserts.fail("验证码已失效");
        }
        if (!code.equalsIgnoreCase(cacheObject)) {
            Asserts.fail("验证码错误");
        }
    }
}
