package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.AdminRoleMapper;
import com.atguigu.ssyx.acl.mapper.RoleMapper;
import com.atguigu.ssyx.acl.service.AdminRoleService;
import com.atguigu.ssyx.acl.service.RoleService;
import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.acl.AdminRole;
import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService{

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private AdminRoleService adminRoleService;
    @Override
    public IPage<Role> selectPage(IPage<Role> pageParam, RoleQueryVo roleQueryVo) {
        String roleName = roleQueryVo.getRoleName();
        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (roleName != null ){
            //封装查询参数
            roleLambdaQueryWrapper.like(Role::getRoleName,roleName);
        }
        IPage<Role> roleList = baseMapper.selectPage(pageParam, roleLambdaQueryWrapper);
        return roleList;


    }

    @Override
    public Map<String, Object> findRoleByUserId(Long adminId) {
        //1，获取所有角色数据
        List<Role> allRolesList = baseMapper.selectList(null);
        //2，根据id获取角色数据
        //2,1去admin_role表中根据id查询数据
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId,adminId);
        List<AdminRole> adminRoles = adminRoleService.list(wrapper);
        List<Role> assignRoles =null;
        if (!adminRoles.isEmpty()){
            //2,2获取其中的角色id
            List<Long> roleIdList = adminRoles.stream().map(adminRole -> adminRole.getRoleId()).collect(Collectors.toList());
            //2，3跟据角色id查询角色
            assignRoles = roleMapper.selectBatchIds(roleIdList);
        }



        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }


    /**
     * 分配角色
     * @param adminId
     * @param roleIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRoleRealtionShip(Long adminId, Long[] roleIds) {
        //删除用户分配的角色数据
        LambdaQueryWrapper<AdminRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AdminRole::getAdminId,adminId);
        adminRoleService.remove(wrapper);

        //分配新的角色
        List<AdminRole> userRoleList = new ArrayList<>();
        for(Long roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)) continue;
            AdminRole userRole = new AdminRole();
            userRole.setAdminId(adminId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        adminRoleService.saveBatch(userRoleList);
    }


}
