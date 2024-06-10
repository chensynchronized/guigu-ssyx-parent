package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.PermissionMapper;
import com.atguigu.ssyx.acl.service.PermissionService;
import com.atguigu.ssyx.acl.service.RolePermissionService;
import com.atguigu.ssyx.model.acl.Permission;
import com.atguigu.ssyx.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.acl.helper.PermissionHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Resource
    private RolePermissionService rolePermissionService;
    //获取所有菜单
    @Override
    public List<Permission> selectList() {
        //获取全部权限数据
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(allPermissionList);
        return result;
    }

    //递归删除菜单
    @Override
    public boolean removeChildById(Long id) {
        List<Long> idList = new ArrayList<>();
        this.selectChildListById(id, idList);
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public Map<String, Object> findPermissionByUserId(Long roleId) {
        //1,获取所有菜单数据，树形
        //1，1获取全部权限数据
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //1，2把权限数据构建成树形结构数据
        List<Permission> allPermissionsList = PermissionHelper.bulid(allPermissionList);
        //2 根据角色id查询角色分配所有权限


        //2，根据id获取角色对应的菜单数据
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId,roleId);
        List<RolePermission> list = rolePermissionService.list(wrapper);
        List permissionIds = null;
        if (!list.isEmpty()){
            permissionIds = list.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());


        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("permissionIds", permissionIds);
        roleMap.put("allPermissions", allPermissionsList);
        return roleMap;
    }

    @Override
    public void saveRolePermissionRealtionShip(Long roleId, Long[] permissionId) {
        //删除角色原有的权限
        LambdaQueryWrapper<RolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolePermission::getRoleId , roleId);
        rolePermissionService.remove(wrapper);

        //给角色添加新的权限
        ArrayList<RolePermission> list = new ArrayList<>();
        for (Long id:permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(id);
            rolePermission.setRoleId(roleId);
            list.add(rolePermission);

        }
        rolePermissionService.saveBatch(list);

    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(Long id, List<Long> idList) {

        LambdaQueryWrapper<Permission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Permission::getPid,id);
        List<Permission> permissions = baseMapper.selectList(wrapper);
        permissions.stream().forEach(permission -> {
            idList.add(permission.getId());
            if (permission.getLevel() < 4){
                this.selectChildListById(permission.getId(),idList);
            }
        });

    }
}
