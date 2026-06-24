package com.yuebing.ybmall.product.controller;

import com.yuebing.ybmall.common.utils.R;
import com.yuebing.ybmall.product.entity.CategoryBrandRelationEntity;
import com.yuebing.ybmall.product.service.CategoryBrandRelationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 品牌分类关联接口。
 *
 * <p>提供分类和品牌之间关联关系的查询能力。</p>
 */
@RestController
public class CategoryBrandRelationController {

    private final CategoryBrandRelationService categoryBrandRelationService;

    public CategoryBrandRelationController(CategoryBrandRelationService categoryBrandRelationService) {
        this.categoryBrandRelationService = categoryBrandRelationService;
    }

    /**
     * 查询指定分类下关联的品牌列表。
     *
     * @param catId 分类 ID
     * @return 该分类下关联的品牌列表
     */
    @GetMapping("/product/categorybrandrelation/brands/list")
    public R brandsList(@RequestParam Long catId) {
        List<CategoryBrandRelationEntity> brands = categoryBrandRelationService.getBrandsByCatId(catId);
        return R.ok().put("data", brands);
    }
}
