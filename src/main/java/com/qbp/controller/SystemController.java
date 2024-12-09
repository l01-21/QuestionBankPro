package com.qbp.controller;

import com.qbp.model.dto.RegisterDTO;
import com.qbp.model.vo.Result;
import com.qbp.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.qbp.model.vo.Result.error;
import static com.qbp.model.vo.Result.success;

/**
 *  系统 前端控制器
 *
 * @author lll
 * @since 2024-12-09
 */
@RestController
@RequestMapping
public class SystemController {
    @Resource
    private SystemService systemService;
    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO user) {
        String msg = systemService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

}
