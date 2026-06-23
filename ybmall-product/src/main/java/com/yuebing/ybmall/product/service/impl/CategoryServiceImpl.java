package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.exception.BizException;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.mapper.CategoryMapper;
import com.yuebing.ybmall.product.service.CategoryService;
import com.yuebing.ybmall.product.vo.CategorySaveVo;
import com.yuebing.ybmall.product.vo.CategoryUpdateVo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {


    /**
     * 获取所有分类
     * @return
     */
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

    /**
     * 新增分类
     * @param categorySaveVo
     */
    @Override
    public void saveCategory(CategorySaveVo categorySaveVo) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categorySaveVo.getName());
        categoryEntity.setParentCid(categorySaveVo.getParentCid());
        categoryEntity.setCatLevel(categorySaveVo.getCatLevel());
        categoryEntity.setSort(categorySaveVo.getSort());
        categoryEntity.setIcon(categorySaveVo.getIcon());
        categoryEntity.setProductUnit(categorySaveVo.getProductUnit());
        categoryEntity.setShowStatus(1);
        categoryEntity.setProductCount(0);

        this.save(categoryEntity);
    }

    /**
     * 通过id查询分类
     * @param catId
     * @return
     */
    @Override
    public CategoryEntity getCategoryById(Long catId) {
        CategoryEntity category = this.getById(catId);

        if (category == null) {
            throw new BizException(BizCodeEnum.PRODUCT_CATEGORY_NOT_FOUND);
        }

        return category;
    }

    /**
     * 更新分类
     * @param categoryUpdateVo
     */
    @Override
    public void updateCategory(CategoryUpdateVo categoryUpdateVo) {
        CategoryEntity category = this.getById(categoryUpdateVo.getCatId());

        if (category == null) {
            throw new BizException(BizCodeEnum.PRODUCT_CATEGORY_NOT_FOUND);
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCatId(categoryUpdateVo.getCatId());
        categoryEntity.setName(categoryUpdateVo.getName());
        categoryEntity.setParentCid(categoryUpdateVo.getParentCid());
        categoryEntity.setCatLevel(categoryUpdateVo.getCatLevel());
        categoryEntity.setSort(categoryUpdateVo.getSort());
        categoryEntity.setIcon(categoryUpdateVo.getIcon());
        categoryEntity.setProductUnit(categoryUpdateVo.getProductUnit());

        this.updateById(categoryEntity);
    }

    /**
     * 批量逻辑删除分类
     * @param catIds
     */
    @Override
    public void removeCategoryByIds(List<Long> catIds) {
        for (Long catId : catIds) {
            long count = this.lambdaQuery()
                    .eq(CategoryEntity::getParentCid, catId)
                    .count();

            if (count > 0) {
                throw new BizException(BizCodeEnum.PRODUCT_CATEGORY_HAS_CHILDREN);
            }
        }

        this.removeByIds(catIds);
    }
}