package com.qbp.service;

import com.qbp.model.entity.Resource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 权限资源表 服务类
 *
 * @author lll
 * @since 2024-12-09
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 根据用户id获取资源
     * @param id 用户id
     * @return 资源列表
     */
    List<Resource> getResources(Long id);

    /**
     * 根据id删除资源
     * @param id 资源id
     */
    void deleteResourceById(Long id);
}
