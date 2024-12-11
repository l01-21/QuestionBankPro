package com.qbp.service;

import com.qbp.model.entity.Menu;
import com.qbp.model.entity.Resource;
import com.qbp.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户表 服务类
 *
 * @author lll
 * @since 2024-12-09
 */
public interface UserService extends IService<User> {
    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean checkUserNameUnique(User user);

}
