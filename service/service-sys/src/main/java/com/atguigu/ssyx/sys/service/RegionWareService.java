package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RegionWareService extends IService<RegionWare> {
    IPage<RegionWare> selectPage(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo);

    void saveRegionSave(RegionWare regionWare);

    void updateStatus(Long id, Integer status);
}
