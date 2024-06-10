package com.atguigu.ssyx.acl.service;

import com.atguigu.ssyx.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface PermissionService extends IService<Permission> {
    List<Permission> selectList();

    boolean removeChildById(Long id);

    Map<String, Object> findPermissionByUserId(Long roleId);

    void saveRolePermissionRealtionShip(Long roleId, Long[] permissionId);

}
