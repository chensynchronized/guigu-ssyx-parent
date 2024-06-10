package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/acl/role")
@Slf4j
@CrossOrigin
public class RoleController {
    @Resource
    private RoleService roleService;

    //1，获取角色分页列表
    /**
     *       url: `${api_name}/${page}/${limit}`,
     *       method: 'get',
     */
    @ApiOperation("获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public Result list(@ApiParam(name = "page", value = "当前页码", required = true)
                           @PathVariable Long page,

                       @ApiParam(name = "limit", value = "每页记录数", required = true)
                           @PathVariable Long limit,

                       @ApiParam(name = "roleQueryVo", value = "查询对象", required = false)
                                   RoleQueryVo roleQueryVo) {
        Page<Role> pageParam = new Page<>(page, limit);
        IPage<Role> pageModel = roleService.selectPage(pageParam, roleQueryVo);
        return Result.ok(pageModel);

    }

    //2，根据id获取角色
    /**
     *       url: `${api_name}/get/${id}`,
     *       method: 'get'
     */
    @ApiOperation("根据id获取角色")
    @GetMapping("get/{id}")
    public Result getRoleById(@PathVariable Long id){
        Role role = roleService.getById(id);
        if (role == null){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(role);
    }

    //3，新增角色
    /**
     *     url: `${api_name}/save`,
     *       method: 'post',
     */
    @ApiOperation("新增角色")
    @PostMapping("save")
    public Result save(@RequestBody Role role){
        boolean save = roleService.save(role);
        if (!save){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(null);
    }

    //4，修改角色
    /**
     *    url: `${api_name}/update`,
     *       method: 'put',
     */
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody Role role){
        if(role.getId() == null && role.getRoleName() == null){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        roleService.updateById(role);
        return Result.ok(null);
    }

    //5，根据id删除角色
    /**
     *       url: `${api_name}/remove/${id}`,
     *       method: 'delete'
     */
    @ApiOperation("删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        if(id == null){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }

        roleService.removeById(id);
        return Result.ok(null);
    }

    //6，根据id批量删除角色
    /**
     *       url: `${api_name}/batchRemove`,
     *       method: 'delete',
     *       data: ids
     */
    @ApiOperation("批量删除角色")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        if (idList.isEmpty()){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        roleService.removeByIds(idList);
        return Result.ok(null);

    }


}
