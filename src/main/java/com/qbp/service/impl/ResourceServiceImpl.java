package com.qbp.service.impl;

import com.qbp.model.entity.Resource;
import com.qbp.mapper.ResourceMapper;
import com.qbp.service.ResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 权限资源表 服务实现类
 *
 * @author lll
 * @since 2024-12-09
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements ResourceService {

}
