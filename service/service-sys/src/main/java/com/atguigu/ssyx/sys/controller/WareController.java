package com.atguigu.ssyx.sys.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Ware;
import com.atguigu.ssyx.sys.service.WareService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "仓库管理", tags = "仓库管理")
@RestController
@RequestMapping(value="/admin/sys/ware")
@CrossOrigin
public class WareController {
    @Resource
    private WareService wareService;
    /**
     *       url: `${api_name}/findAllList`,
     *       method: 'get'
     */
    @ApiOperation("获取全部仓库")
    @GetMapping("findAllList")
    public Result findAllList(){
        return Result.ok(wareService.list());
    }
    /**
     *       url: `${api_name}/save`,
     *       method: 'post',
     *       data: role
     */
    @ApiOperation("添加仓库")
    @PostMapping("save")
    public Result save(@RequestBody Ware ware){
        wareService.save(ware);
        return Result.ok(null);
    }
    /**
     *       url: `${api_name}/update`,
     *       method: 'put',
     *       data: role
     */
    @ApiOperation("修改仓库")
    @PutMapping("update")
    public Result update(@RequestBody Ware ware){
        wareService.update(ware,null);
        return Result.ok(null);
    }

    /**
     *       url: `${api_name}/remove/${id}`,
     *       method: 'delete'
     */
    @ApiOperation("根据id删除仓库")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        wareService.removeById(id);
        return Result.ok(null);
    }

    /**
     *       url: `${api_name}/batchRemove`,
     *       method: 'delete',
     *       data: idList
     */
    @ApiOperation("根据id批量删除仓库")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List idList){
        wareService.removeByIds(idList);
        return Result.ok(null);
    }
}
