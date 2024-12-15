package com.qbp.service;

import com.qbp.model.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 角色表 服务类
 *
 * @author lll
 * @since 2024-12-09
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据id删除角色
     * @param id id
     */
    void deleteRoleById(Long id);

    /**
     * 启用/禁用状态
     * @param id id
     */
    void updateStatus(Long id);

    /**
     * 分配资源
     * @param roleId 角色id
     * @param resourceIds 资源ids
     */
    void assignResource(Long roleId, Long[] resourceIds);
}
