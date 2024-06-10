package com.atguigu.ssyx.acl.controller;

import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
@CrossOrigin //跨域
public class PermissionAdminController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "获取菜单")
    @GetMapping
    public Result index() {
        List<Permission> list = permissionService.selectList();
        return Result.ok(list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Result updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }
    //查看角色的权限列表
    /**
     *       url: `${api_name}/toAssign/${roleId}`,
     *       method: 'get'
     */
    @ApiOperation("查看角色的权限列表")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        Map<String,Object> permissionMap = permissionService.findPermissionByUserId(roleId);
        return Result.ok(permissionMap);
    }
    //给某个角色授权
    /**
     *       url: `${api_name}/doAssign`,
     *       method: "post",
     *       params: {roleId, permissionId}
     */
    @ApiOperation("给指定角色授权")
    @PostMapping("doAssign")
    public Result doAssign(@RequestParam Long roleId,@RequestParam Long[] permissionId){
        permissionService.saveRolePermissionRealtionShip(roleId,permissionId);
        return Result.ok(null);
    }
}
