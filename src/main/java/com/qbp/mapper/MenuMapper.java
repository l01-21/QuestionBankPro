package com.qbp.mapper;

import com.qbp.model.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 角色菜单表 Mapper 接口
 *
 * @author lll
 * @since 2024-12-09
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id获取菜单
     *
     * @param id 用户id
     * @return 菜单列表
     */
    List<Menu> getMenus(Long id);
}
