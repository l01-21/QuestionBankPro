package com.qbp.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录对象
 */
@Setter
@Getter
public class LoginDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
}
