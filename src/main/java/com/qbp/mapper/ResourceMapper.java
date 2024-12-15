package com.qbp.mapper;

import com.qbp.model.entity.Resource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 权限资源表 Mapper 接口
 *
 * @author lll
 * @since 2024-12-09
 */
public interface ResourceMapper extends BaseMapper<Resource> {
    /**
     * 根据用户id获取资源
     * @param id 用户id
     * @return 资源列表
     */
    List<Resource> getResourcesByUserId(Long id);

    /**
     * 根据角色id获取资源
     * @param roleId 角色id
     * @return 资源列表
     */
    List<Resource> getResourcesByRoleId(Long roleId);
}
