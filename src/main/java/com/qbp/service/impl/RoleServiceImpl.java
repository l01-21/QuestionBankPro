package com.qbp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qbp.enums.CommonStatus;
import com.qbp.exception.Asserts;
import com.qbp.model.entity.Role;
import com.qbp.mapper.RoleMapper;
import com.qbp.model.entity.RoleResourceRelation;
import com.qbp.model.entity.UserRoleRelation;
import com.qbp.service.RoleResourceRelationService;
import com.qbp.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qbp.service.UserRoleRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色表 服务实现类
 *
 * @author lll
 * @since 2024-12-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private UserRoleRelationService userRoleRelationService;
    @Resource
    private RoleResourceRelationService roleResourceRelationService;

    @Override
    public void deleteRoleById(Long id) {
        List<UserRoleRelation> list = userRoleRelationService.list(
                new QueryWrapper<UserRoleRelation>()
                        .lambda()
                        .eq(UserRoleRelation::getRoleId, id)
        );
        if (!list.isEmpty()) {
            Asserts.fail("该角色已分配用户，无法删除");
        }
        roleResourceRelationService.remove(
                new QueryWrapper<RoleResourceRelation>()
                        .lambda()
                        .eq(RoleResourceRelation::getRoleId, id)
        );
        Role role = baseMapper.selectById(id);
        role.setDelFlag(CommonStatus.DELETED.getCode());
        baseMapper.updateById(role);
    }

    @Override
    public void updateStatus(Long id) {
        Role role = baseMapper.selectById(id);
        if (CommonStatus.OK.getCode().equals(role.getStatus())) {
            List<UserRoleRelation> list = userRoleRelationService.list(
                    new QueryWrapper<UserRoleRelation>()
                            .lambda()
                            .eq(UserRoleRelation::getRoleId, id)
            );
            if (!list.isEmpty()) {
                Asserts.fail("该角色已分配用户，无法停用");
            }
            role.setStatus(CommonStatus.DISABLE.getCode());
        } else {
            role.setStatus(CommonStatus.OK.getCode());
        }
        baseMapper.updateById(role);
    }

    @Override
    public void assignResource(Long roleId, Long[] resourceIds) {
        roleResourceRelationService.remove(
                new QueryWrapper<RoleResourceRelation>()
                        .lambda()
                        .eq(RoleResourceRelation::getRoleId, roleId)
        );
        List<RoleResourceRelation> list = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            RoleResourceRelation relation = new RoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            list.add(relation);
        }
        roleResourceRelationService.saveBatch(list);
    }
}
