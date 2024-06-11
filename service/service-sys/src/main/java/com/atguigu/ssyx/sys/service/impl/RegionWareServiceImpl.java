package com.atguigu.ssyx.sys.service.impl;

import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.sys.RegionWare;

import com.atguigu.ssyx.sys.mapper.RegionWareMapper;
import com.atguigu.ssyx.sys.service.RegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service

public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    @Resource
    private RegionWareMapper regionWareMapper;

    //开通区域列表
    @Override
    public IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
        String keyword = regionWareQueryVo.getKeyword();
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            wrapper.like(RegionWare::getRegionName,keyword)
                    .or().like(RegionWare::getWareName,keyword);
        }

        IPage<RegionWare> regionWarePage = baseMapper.selectPage(pageParam, wrapper);
        return regionWarePage;
    }

    @Override
    public void saveRegionSave(RegionWare regionWare) {
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionWare::getRegionId,regionWare.getRegionId());
        Integer count = baseMapper.selectCount(wrapper);
        if (count>0){
            throw new SsyxException(ResultCodeEnum.REGION_OPEN);
        }
        baseMapper.insert(regionWare);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        RegionWare regionWare = new RegionWare();
        regionWare.setStatus(status);
        regionWare.setId(id);
        baseMapper.updateById(regionWare);
    }
}
