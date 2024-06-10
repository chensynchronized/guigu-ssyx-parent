package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Api(tags = "登录")
@RestController
@RequestMapping("/admin/acl/index")
@CrossOrigin     //跨域
@Slf4j
public class IndexController {

    /**
     * 1、请求登陆的login
     */
    @ApiOperation("登录")
    @PostMapping("login")
    public Result login() {
        log.info("登录");
        Map<String,Object> map = new HashMap<>();
        map.put("token","admin-token");
        return Result.ok(map);
    }
    /**
     * 2 获取用户信息
     */
    @ApiOperation("获取登录信息")
    @GetMapping("info")
    public Result info(){
        log.info("获取登录信息");
        Map<String,Object> map = new HashMap<>();
        map.put("name","陈浩铭");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    /**
     * 3 退出
     */
    @ApiOperation("退出登录")
    @PostMapping("logout")
    public Result logout(){
        log.info("退出登录");
        return Result.ok(null);
    }

}