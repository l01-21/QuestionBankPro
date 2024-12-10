package com.qbp.service;

import com.qbp.model.dto.RegisterDTO;
import com.qbp.model.vo.Result;

/**
 * 系统服务接口
 */
public interface SystemService {
    /**
     * 注册
     * @param registerDTO 注册用户信息
     * @return 错误信息
     */
    String register(RegisterDTO registerDTO);

    /**
     * 获取验证码
     * @return 验证码
     */
    Result getCode();

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return token
     */
    String login(String username, String password, String code, String uuid);
}
