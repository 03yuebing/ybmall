package com.yuebing.ybmall.product.controller;

import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.utils.R;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.service.CategoryService;
import com.yuebing.ybmall.product.vo.CategorySaveVo;
import com.yuebing.ybmall.product.vo.CategoryUpdateVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/product/category/list/tree")
    public R listWithTree() {
        List<CategoryEntity> categories = categoryService.listWithTree();
        return R.ok().put("data", categories);
    }

    @PostMapping("/product/category/save")
    public R save(@Valid @RequestBody CategorySaveVo categorySaveVo) {
        categoryService.saveCategory(categorySaveVo);
        return R.ok();
    }

    @GetMapping("/product/category/info/{catId}")
    public R info(@PathVariable Long catId) {
        CategoryEntity category = categoryService.getCategoryById(catId);
        return R.ok().put("data", category);
    }

    @PostMapping("/product/category/update")
    public R update(@Valid @RequestBody CategoryUpdateVo categoryUpdateVo) {
        categoryService.updateCategory(categoryUpdateVo);
        return R.ok();
    }

    /**
     * 批量逻辑删除分类
     * @param catIds
     * @return
     */
    @PostMapping("/product/category/delete")
    public R delete(@RequestBody List<Long> catIds) {
        categoryService.removeCategoryByIds(catIds);
        return R.ok();
    }

}