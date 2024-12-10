package com.qbp.service.impl;

import com.qbp.model.entity.Role;
import com.qbp.mapper.RoleMapper;
import com.qbp.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色表 服务实现类
 *
 * @author lll
 * @since 2024-12-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
