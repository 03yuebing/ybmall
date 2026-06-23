package com.yuebing.ybmall.product.controller;

import com.yuebing.ybmall.common.utils.R;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}