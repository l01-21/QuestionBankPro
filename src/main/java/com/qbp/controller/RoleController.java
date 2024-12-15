package com.qbp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qbp.enums.CommonStatus;
import com.qbp.model.entity.Role;
import com.qbp.model.vo.PageResult;
import com.qbp.model.vo.Result;
import com.qbp.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色表 前端控制器
 *
 * @author lll
 * @since 2024-12-09
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;
    @GetMapping(value = "/page", name = "分页查询角色")
    public PageResult<Role> page(@RequestParam(defaultValue = "1") int pageNum,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                 @RequestParam(required = false) String name) {
        IPage<Role> page = roleService.page(new Page<>(pageNum, pageSize),
                new QueryWrapper<Role>()
                        .lambda()
                        .like(name != null, Role::getName, name)
                        .ne(Role::getDelFlag, CommonStatus.DELETED.getCode())
        );
        return new PageResult<Role>(page);
    }
    @GetMapping(value = "/{id}", name = "根据id查询角色")
    public Result getById(@PathVariable("id") Long id) {
        return Result.success(roleService.getById(id));
    }

    @PutMapping(value = "", name = "修改角色")
    public Result update(@RequestBody Role role) {
        role.setStatus(null);
        role.setDelFlag(null);
        roleService.updateById(role);
        return Result.success();
    }

    @PostMapping(value = "", name = "添加角色")
    public Result add(@RequestBody Role role) {
        role.setStatus(CommonStatus.DISABLE.getCode());
        role.setDelFlag(CommonStatus.OK.getCode());
        roleService.save(role);
        return Result.success();
    }

    @DeleteMapping(value = "/{id}", name = "删除角色")
    public Result delete(@PathVariable("id") Long id) {
        roleService.deleteRoleById(id);
        return Result.success();
    }

    @PutMapping(value = "/{id}/status", name = "启用/禁用角色")
    public Result updateStatus(@PathVariable Long id) {
        roleService.updateStatus(id);
        return Result.success();
    }

    @PostMapping(value = "/assign", name = "分配资源")
    public Result assignResource(@RequestParam("roleId") Long roleId,
                                 @RequestParam("resourceIds") Long[] resourceIds) {
        roleService.assignResource(roleId, resourceIds);
        return Result.success();
    }
}

