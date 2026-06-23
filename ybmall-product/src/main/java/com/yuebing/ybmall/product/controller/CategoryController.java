package com.yuebing.ybmall.product.controller;

import com.yuebing.ybmall.common.utils.R;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.service.CategoryService;
import com.yuebing.ybmall.product.vo.CategorySaveVo;
import com.yuebing.ybmall.product.vo.CategoryUpdateVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类管理接口。
 *
 * <p>提供后台管理系统使用的分类树查询、新增、详情、更新和批量删除能力。
 * Controller 只负责接收请求和返回统一响应，具体业务规则放在 Service 层。</p>
 */
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 查询商品三级分类树。
     *
     * @return 分类树数据
     */
    @GetMapping("/product/category/list/tree")
    public R listWithTree() {
        List<CategoryEntity> categories = categoryService.listWithTree();
        return R.ok().put("data", categories);
    }

    /**
     * 新增商品分类。
     *
     * @param categorySaveVo 新增分类请求参数
     * @return 操作结果
     */
    @PostMapping("/product/category/save")
    public R save(@Valid @RequestBody CategorySaveVo categorySaveVo) {
        categoryService.saveCategory(categorySaveVo);
        return R.ok();
    }

    /**
     * 查询分类详情。
     *
     * @param catId 分类 ID
     * @return 分类详情
     */
    @GetMapping("/product/category/info/{catId}")
    public R info(@PathVariable Long catId) {
        CategoryEntity category = categoryService.getCategoryById(catId);
        return R.ok().put("data", category);
    }

    /**
     * 更新商品分类。
     *
     * @param categoryUpdateVo 更新分类请求参数
     * @return 操作结果
     */
    @PostMapping("/product/category/update")
    public R update(@Valid @RequestBody CategoryUpdateVo categoryUpdateVo) {
        categoryService.updateCategory(categoryUpdateVo);
        return R.ok();
    }

    /**
     * 批量逻辑删除商品分类。
     *
     * @param catIds 待删除的分类 ID 列表
     * @return 操作结果
     */
    @PostMapping("/product/category/delete")
    public R delete(@RequestBody List<Long> catIds) {
        categoryService.removeCategoryByIds(catIds);
        return R.ok();
    }
}
