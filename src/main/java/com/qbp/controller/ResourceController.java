package com.qbp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qbp.model.entity.Resource;
import com.qbp.model.vo.PageResult;
import com.qbp.model.vo.Result;
import com.qbp.service.ResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限资源表 前端控制器
 *
 * @author lll
 * @since 2024-12-09
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {
    @javax.annotation.Resource
    private ResourceService resourceService;

    @GetMapping(value = "/page", name = "分页查询资源")
    public PageResult<Resource> page(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize,
                       @RequestParam(required = false) String name) {
        IPage<Resource> page = resourceService.page(new Page<>(pageNum, pageSize),
                new QueryWrapper<Resource>()
                        .lambda()
                        .like(name != null, Resource::getName, name)
        );
        return new PageResult<Resource>(page);
    }
}

