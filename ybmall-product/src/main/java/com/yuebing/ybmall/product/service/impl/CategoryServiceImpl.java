package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.mapper.CategoryMapper;
import com.yuebing.ybmall.product.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> entities = baseMapper.selectList(null);

        Map<Long, List<CategoryEntity>> parentIdMap = entities.stream()
                .collect(Collectors.groupingBy(CategoryEntity::getParentCid));

        return parentIdMap.getOrDefault(0L, List.of()).stream()
                .peek(category -> category.setChildren(getChildren(category, parentIdMap)))
                .sorted(Comparator.comparingInt(category -> category.getSort() == null ? 0 : category.getSort()))
                .toList();
    }

    private List<CategoryEntity> getChildren(CategoryEntity root, Map<Long, List<CategoryEntity>> parentIdMap) {
        return parentIdMap.getOrDefault(root.getCatId(), List.of()).stream()
                .peek(category -> category.setChildren(getChildren(category, parentIdMap)))
                .sorted(Comparator.comparingInt(category -> category.getSort() == null ? 0 : category.getSort()))
                .toList();
    }
}