package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.product.mapper.CategoryMapper;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2023-04-04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    //商品分类分页列表
    @Override
    public IPage<Category> selectPage(Page<Category> pageParam, CategoryQueryVo categoryQueryVo) {
        String name = categoryQueryVo.getName();
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(name)) {
            queryWrapper.like(Category::getName,name);
        }
        IPage<Category> categoryPage = baseMapper.selectPage(pageParam, queryWrapper);
        return categoryPage;
    }

    @Override
    public List<Category> findAllList() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        List<Category> categoryList = baseMapper.selectList(wrapper);
        return categoryList;
    }
}
