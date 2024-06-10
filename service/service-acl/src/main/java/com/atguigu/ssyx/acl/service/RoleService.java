package com.atguigu.ssyx.acl.service;


import com.atguigu.ssyx.model.acl.Role;
import com.atguigu.ssyx.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<Role> {
    IPage<Role> selectPage(IPage<Role> pageParam, RoleQueryVo roleQueryVo);


    Map<String, Object> findRoleByUserId(Long adminId);

    void saveUserRoleRealtionShip(Long adminId, Long[] roleIds);
}
