package com.qbp.controller;

import com.qbp.constant.Constants;
import com.qbp.model.dto.LoginDTO;
import com.qbp.model.dto.RegisterDTO;
import com.qbp.model.vo.Result;
import com.qbp.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.UUID;

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
    @PostMapping(value = "/register", name = "用户注册")
    public Result register(@RequestBody RegisterDTO user) {
        String msg = systemService.register(user);
        return StringUtils.isEmpty(msg) ? success() : error(msg);
    }

    @GetMapping(value = "/captchaImage", name = "生成验证码")
    public Result getCode() {
        return systemService.getCode();
    }

    @PostMapping(value = "/login", name = "用户登录")
    public Result login(@RequestBody LoginDTO user) {
        Result result = Result.success();
        String token = systemService.login(user.getUsername(), user.getPassword(), user.getCode(), user.getUuid());
        result.put(Constants.TOKEN, token);
        return result;
    }
}
