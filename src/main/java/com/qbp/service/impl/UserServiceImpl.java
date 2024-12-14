package com.qbp.service.impl;

import com.qbp.constant.UserConstants;
import com.qbp.model.entity.User;
import com.qbp.mapper.UserMapper;
import com.qbp.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务实现类
 *
 * @author lll
 * @since 2024-12-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public boolean checkUserNameUnique(User user) {
        User info = baseMapper.checkUserNameUnique(user.getUsername());
        return info != null ? UserConstants.NOT_UNIQUE : UserConstants.UNIQUE;
    }


}
