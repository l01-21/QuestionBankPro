package com.qbp.service.impl;

import com.google.code.kaptcha.Producer;
import com.qbp.constant.CacheConstants;
import com.qbp.constant.Constants;
import com.qbp.constant.UserConstants;
import com.qbp.enums.CommonStatus;
import com.qbp.exception.Asserts;
import com.qbp.model.dto.RegisterDTO;
import com.qbp.model.entity.User;
import com.qbp.model.vo.LoginVO;
import com.qbp.model.vo.Result;
import com.qbp.service.ResourceService;
import com.qbp.service.SystemService;
import com.qbp.service.UserService;
import com.qbp.utils.RedisCacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    private ResourceService resourceService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource
    private TokenService tokenService;

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

    @Override
    public Result getCode() {
        Result success = Result.success();
        // 保存验证码信息
        String uuid = UUID.randomUUID().toString();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;
        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        capStr = code = captchaProducer.createText();
        image = captchaProducer.createImage(capStr);

        redisCacheUtils.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return Result.error(e.getMessage());
        }
        success.put("uuid", uuid);
        success.put("img", Base64.getEncoder().encodeToString(os.toByteArray()));
        return success;
    }

    @Override
    public String login(String username, String password, String code, String uuid) {
        // 验证码校验
        validateCaptcha(code, uuid);
        // 登录前置校验
        loginPreCheck(username, password);
        // 用户校验
        User user = userService.lambdaQuery().eq(User::getUsername, username).eq(User::getDelFlag, CommonStatus.OK.getCode()).one();
        // 校验密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            Asserts.fail("用户名或密码错误");
        }
        // 校验是否可用
        if (CommonStatus.DISABLE.getCode().equals(user.getStatus())) {
            Asserts.fail("账号已停用");
        }
        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);
        // 设置权限
        loginVO.setResources(resourceService.getResourcesByUserId(user.getId()));
        return tokenService.createToken(loginVO);
    }


    /**
     * 登录前置校验
     *
     * @param username 用户名
     * @param password 密码
     */
    private void loginPreCheck(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            Asserts.fail("用户名或密码为空");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            Asserts.fail("密码长度必须在" + UserConstants.PASSWORD_MIN_LENGTH + "-" + UserConstants.PASSWORD_MAX_LENGTH + "之间");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            Asserts.fail("用户名长度必须在" + UserConstants.USERNAME_MIN_LENGTH + "-" + UserConstants.USERNAME_MAX_LENGTH + "之间");
        }
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
        if (cacheObject == null) {
            Asserts.fail("验证码已失效，请重新获取验证码");
        }
        if (!cacheObject.equalsIgnoreCase(code)) {
            Asserts.fail("验证码错误");
        }
        redisCacheUtils.deleteObject(verifyKey);
    }
}
