package com.atguigu.ssyx.acl.service.impl;

import com.atguigu.ssyx.acl.mapper.AdminMapper;
import com.atguigu.ssyx.acl.service.AdminService;
import com.atguigu.ssyx.model.acl.Admin;
import com.atguigu.ssyx.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public IPage<Admin> selectPage(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String username = adminQueryVo.getUsername();

        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if(username != null){
            wrapper.like(Admin::getUsername,username);
        }
        IPage<Admin> pageModel = baseMapper.selectPage(pageParam, wrapper);

        return pageModel;
    }
}
