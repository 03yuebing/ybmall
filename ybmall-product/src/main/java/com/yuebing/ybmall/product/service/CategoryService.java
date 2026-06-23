package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.vo.CategorySaveVo;
import com.yuebing.ybmall.product.vo.CategoryUpdateVo;

import java.util.List;

public interface CategoryService extends IService<CategoryEntity> {

    List<CategoryEntity> listWithTree();

    void saveCategory(CategorySaveVo categorySaveVo);

    CategoryEntity getCategoryById(Long catId);

    void updateCategory(CategoryUpdateVo categoryUpdateVo);

    void removeCategoryByIds(List<Long> catIds);
}