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

    /**
     * 根据用户id获取菜单
     * @param id 用户id
     * @return 菜单列表
     */
    List<Menu> getMenus(Long id);

    /**
     * 根据用户id获取资源
     * @param id 用户id
     * @return 资源列表
     */
    List<Resource> getResources(Long id);
}
