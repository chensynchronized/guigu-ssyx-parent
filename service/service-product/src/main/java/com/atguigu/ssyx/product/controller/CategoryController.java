package com.atguigu.ssyx.product.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "Category管理", tags = "商品分类管理")
@RestController
@RequestMapping(value="/admin/product/category")
@SuppressWarnings({"unchecked", "rawtypes"})
@CrossOrigin
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     *       url: `${api_name}/${page}/${limit}`,
     *       method: 'get',
     *       params: searchObj
     */
    @ApiOperation("获取商品分类分页列表")
    @GetMapping("{page}/{limit}")
    public Result list(@PathVariable Long page,
                       @PathVariable Long limit,
                       CategoryQueryVo categoryQueryVo){
        Page<Category> pageParam = new Page<>(page, limit);
        IPage<Category> pageModel = categoryService.selectPage(pageParam, categoryQueryVo);
        return Result.ok(pageModel);

    }
    /**
     *      url: `${api_name}/get/${id}`,
     *       method: 'get'
     */
    @ApiOperation("获取商品分类信息")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable Long id){
        Category category = categoryService.getById(id);
        return Result.ok(category);
    }

    /**
     *       url: `${api_name}/save`,
     *       method: 'post',
     *       data: role
     */
    @ApiOperation("新增商品分类信息")
    @PostMapping("/save")
    public Result save(@RequestBody Category category){
        categoryService.save(category);
        return Result.ok(null);
    }
    /**
     *       url: `${api_name}/update`,
     *       method: 'put',
     *       data: role
     */
    @ApiOperation("修改商品分类信息")
    @PutMapping("update")
    public Result update(@RequestBody Category category){
        categoryService.updateById(category);
        return Result.ok(null);
    }

    /**
     *       url: `${api_name}/remove/${id}`,
     *       method: 'delete'
     */
    @ApiOperation("根据id删除上频频分类")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id ){
        categoryService.removeById(id);
        return Result.ok(null);
    }

    /**
     *       url: `${api_name}/batchRemove`,
     *       method: 'delete',
     *       data: idList
     */
    @ApiOperation("根据id批量删除商品分类信息")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestParam List idList){
        categoryService.removeByIds(idList);
        return Result.ok(null);
    }
    /**
     *       url: `${api_name}/findAllList`,
     *       method: 'get'
     */
    @ApiOperation("查询所有商品分类信息")
    @GetMapping("findAllList")
    public Result findAllList(){
        List<Category> categoryList = categoryService.findAllList();
        return Result.ok(categoryList);
    }
}
