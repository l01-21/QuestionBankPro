package com.qbp.service;

import com.qbp.model.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 角色菜单表 服务类
 *
 * @author lll
 * @since 2024-12-09
 */
public interface MenuService extends IService<Menu> {
    /**
     * 根据用户id获取菜单
     *
     * @param id 用户id
     * @return 菜单列表
     */
    List<Menu> getMenus(Long id);
}
