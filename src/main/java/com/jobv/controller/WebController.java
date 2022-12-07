package com.jobv.controller;

import com.jobv.common.Result;
import com.jobv.entity.User;
import com.jobv.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "无权限接口列表")
@RestController
@Slf4j
@AllArgsConstructor
public class WebController {

    private final IUserService userService;

    @GetMapping(value = "/")
    @ApiOperation(value = "版本校验接口")
    public String version() {
        String ver = "partnery-0.0.1-SNAPSHOT";  // 应用版本号
        Package aPackage = WebController.class.getPackage();
        String title = aPackage.getImplementationTitle();
        String version = aPackage.getImplementationVersion();
        if (title != null && version != null) {
            ver = String.join("-", title, version);
        }
        return ver;
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    public Result login(@RequestBody User user){
        User res = userService.login(user);
        return Result.success(res);
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册接口")
    public Result register(@RequestBody User user){
        User res = userService.register(user);
        return Result.success(res);
    }



}
