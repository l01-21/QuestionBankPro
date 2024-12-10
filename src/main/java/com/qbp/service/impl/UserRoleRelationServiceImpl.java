package com.qbp.service.impl;

import com.qbp.model.entity.UserRoleRelation;
import com.qbp.mapper.UserRoleRelationMapper;
import com.qbp.service.UserRoleRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户-角色-关联表 服务实现类
 *
 * @author lll
 * @since 2024-12-09
 */
@Service
public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleRelationMapper, UserRoleRelation> implements UserRoleRelationService {

}
