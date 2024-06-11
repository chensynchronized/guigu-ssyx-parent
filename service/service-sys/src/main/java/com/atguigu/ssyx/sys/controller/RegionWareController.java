package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.RegionWare;

import com.atguigu.ssyx.sys.service.RegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "开通区域管理", tags = "开通区域管理")
@RestController
@RequestMapping(value="/admin/sys/regionWare")
@CrossOrigin
//为什么前后端联调失败，因为两个服务的端口号不一致，前端发请求不知道走哪个服务，得配置nginx
public class RegionWareController {

    @Resource
    private RegionWareService regionWareService;

    //开通区域列表
    @ApiOperation(value = "获取开通区域列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "regionWareVo", value = "查询对象", required = false)
                    RegionWareQueryVo regionWareQueryVo) {

        Page<RegionWare> pageParam = new Page<>(page, limit);

        IPage<RegionWare> pageModel = regionWareService.selectPage(pageParam, regionWareQueryVo);

        return Result.ok(pageModel);
    }

    /**
     *       url: `${api_name}/save`,
     *       method: 'post',
     *       data: role
     */
    @ApiOperation("添加开通区域")
    @PostMapping("save")
    public Result save(@RequestBody RegionWare regionWare){
        regionWareService.saveRegionSave(regionWare);
        return Result.ok(null);
    }
    /**
     *       url: `${api_name}/remove/${id}`,
     *       method: 'delete'
     */
    @ApiOperation("删除开通区域")
    @DeleteMapping("remove/{id}")
    public Result delete(@PathVariable Long id){
        regionWareService.removeById(id);
        return Result.ok(null);
    }
    /**
     *       url: `${api_name}/updateStatus/${id}/${status}`,
     *       method: 'post'
     */
    @ApiOperation("取消开通区域")
    @Update("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,
                               @PathVariable Integer status){
        regionWareService.updateStatus(id,status);
        return Result.ok(null);

    }
    /**
     *       url: `${api_name}/batchRemove`,
     *       method: 'delete',
     *       data: idList
     */
    @ApiOperation("批量删除开通区域")
    @DeleteMapping("delete")
    public Result batchRemove(@RequestBody List idList){
        regionWareService.removeByIds(idList);
        return Result.ok(null);

    }
}