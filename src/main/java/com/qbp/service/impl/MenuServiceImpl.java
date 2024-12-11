package com.qbp.service.impl;

import com.qbp.model.entity.Menu;
import com.qbp.mapper.MenuMapper;
import com.qbp.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色菜单表 服务实现类
 *
 * @author lll
 * @since 2024-12-09
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Override
    public List<Menu> getMenus(Long id) {
        return baseMapper.getMenus(id);
    }
}
