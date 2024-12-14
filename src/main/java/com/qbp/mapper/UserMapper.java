package com.qbp.mapper;

import com.qbp.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户表 Mapper 接口
 *
 * @author lll
 * @since 2024-12-09
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 检查用户名是否唯一
     *
     * @param username 用户名
     * @return 结果
     */
    User checkUserNameUnique(String username);




}
