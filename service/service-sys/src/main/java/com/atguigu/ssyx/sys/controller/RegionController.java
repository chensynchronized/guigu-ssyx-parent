package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.sys.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "区域管理")
@RestController
@RequestMapping("/admin/sys/region")
@CrossOrigin
public class RegionController {
    @Resource
    private RegionService regionService;
    /**
     *       url: `${api_name}/findRegionByKeyword/${keyword}`,
     *       method: 'get'
     */
    @ApiOperation("根据关键字获取地区")
    @GetMapping("findRegionByKeyword/{keyword}")
    public Result findRegionByKeyword(@PathVariable String keyword){
        List<Region> region = regionService.findRegionByKeyword(keyword);
        return Result.ok(region);
    }
}
