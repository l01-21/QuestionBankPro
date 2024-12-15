package com.qbp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qbp.exception.Asserts;
import com.qbp.model.entity.Resource;
import com.qbp.mapper.ResourceMapper;
import com.qbp.model.entity.RoleResourceRelation;
import com.qbp.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qbp.service.RoleResourceRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限资源表 服务实现类
 *
 * @author lll
 * @since 2024-12-09
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

    @javax.annotation.Resource
    private RoleResourceRelationService roleResourceRelationService;

    @Override
    public List<Resource> getResourcesByUserId(Long id) {
        return baseMapper.getResourcesByUserId(id);
    }

    @Override
    public void deleteResourceById(Long id) {
        List<RoleResourceRelation> list = roleResourceRelationService.list(
                new QueryWrapper<RoleResourceRelation>()
                        .lambda()
                        .eq(RoleResourceRelation::getResourceId, id)
        );
        if (!list.isEmpty()) {
            Asserts.fail("该资源已分配角色，无法删除");
        }

        baseMapper.deleteById(id);
    }

    @Override
    public List<Resource> getResourcesByRoleId(Long roleId) {
        return baseMapper.getResourcesByRoleId(roleId);
    }
}
