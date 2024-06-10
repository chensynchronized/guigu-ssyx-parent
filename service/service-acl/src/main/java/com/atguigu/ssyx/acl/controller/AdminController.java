package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags="用户管理")
@RestController
@RequestMapping("/admin/acl/user")
@Slf4j
@CrossOrigin//跨域
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private RoleService roleService;
    //1，获取管理用户分页列表
    /**
     *     url: `${api_name}/${page}/${limit}`,
     *     method: 'get',
     *     params: searchObj
     */
    @ApiOperation("获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       AdminQueryVo adminQueryVo){
        //封装分页条件
        Page<Admin> pageParam = new Page<>(page, limit);
        IPage<Admin> pageModel = adminService.selectPage(pageParam,adminQueryVo);

        return Result.ok(pageModel);
    }
    //2，获取管理用户
    /**
     *     url: `${api_name}/get/${id}`,
     *     method: 'get'
     */
    @ApiOperation("获取管理用户")
    @GetMapping("get/{id}")
    public Result getAdminById(@PathVariable Long id){
        Admin admin = adminService.getById(id);
        if (admin == null){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(admin);

    }


    //3，新增管理用户
    /**
     *     url: `${api_name}/save`,
     *     method: 'post',
     *     data: user
     */
    @ApiOperation("新增管理用户")
    @PostMapping("/save")
    public Result save(@RequestBody Admin admin){
        boolean save = adminService.save(admin);
        if (!save){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(null);
    }

    //4，修该管理用户
/**
 *     url: `${api_name}/update`,
 *     method: 'put',
 *     data: user
 */
    @ApiOperation("修改管理用户")
    @PutMapping("update")
    public Result update(@RequestBody Admin admin){
        if(admin.getId() == null && admin.getName() == null){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        adminService.updateById(admin);
        return Result.ok(null);
    }


    //5，删除管理用户
    /**
     *     url: `${api_name}/remove/${id}`,
     *     method: 'delete'
     */
    @ApiOperation("删除管理用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        if(id == null){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        boolean b = adminService.removeById(id);
        if (!b){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        return Result.ok(null);
    }

    //6，根据id列表批量删除管理用户
    /**
     *     url: `${api_name}/batchRemove`,
     *     method: 'delete',
     *     data: ids
     */
    @ApiOperation("批量删除管理用户")
    @DeleteMapping("batchRomove")
    public Result batchRemove(@RequestBody List idList){
        if(idList.isEmpty()){
            throw new SsyxException(ResultCodeEnum.DATA_ERROR);
        }
        adminService.removeByIds(idList);
        return Result.ok(null);
    }
    //7,获取某个用户的所有角色
    /**
     *     url: `${api_name}/toAssign/${adminId}`,
     *     method: 'get'
     */
    @ApiOperation("获取某个用户的所有角色")
    @GetMapping("toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId){
        Map<String,Object> roleMap = roleService.findRoleByUserId(adminId);
        return Result.ok(roleMap);
    }


    //8，给某个用户分配角色
    /**
     *     url: `${api_name}/doAssign`,
     *     method: 'post',
     *     params: {
     *       adminId,
     *       roleId
     */
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam Long adminId,@RequestParam Long[] roleId) {
        roleService.saveUserRoleRealtionShip(adminId,roleId);
        return Result.ok(null);
    }
}
