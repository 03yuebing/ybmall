package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.CategoryEntity;

import java.util.List;

public interface CategoryService extends IService<CategoryEntity> {

    List<CategoryEntity> listWithTree();
}