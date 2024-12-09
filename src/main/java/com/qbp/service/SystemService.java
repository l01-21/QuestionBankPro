package com.qbp.service;

import com.qbp.model.dto.RegisterDTO;

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
}
