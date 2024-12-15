package com.qbp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qbp.model.entity.Resource;
import com.qbp.model.vo.PageResult;
import com.qbp.model.vo.Result;
import com.qbp.service.ResourceService;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping(value = "/{id}", name = "根据id查询资源")
    public Result getById(@PathVariable("id") Long id) {
        return Result.success(resourceService.getById(id));
    }

    @PutMapping(value = "", name = "修改资源")
    public Result update(@RequestBody Resource resource) {
        resourceService.updateById(resource);
        return Result.success();
    }

    @PostMapping(value = "", name = "添加资源")
    public Result add(@RequestBody Resource resource) {
        resourceService.save(resource);
        return Result.success();
    }

    @DeleteMapping(value = "/{id}", name = "删除资源")
    public Result delete(@PathVariable("id") Long id) {
        resourceService.deleteResourceById(id);
        return Result.success();
    }

    @GetMapping(value = "/getByRole", name = "根据角色获取资源")
    public Result getByRole(@RequestParam("roleId") Long roleId) {
        return Result.success(resourceService.getResourcesByRoleId(roleId));
    }

    @GetMapping(value = "", name = "获取所有资源")
    public Result getAll() {
        return Result.success(resourceService.list());
    }
}

