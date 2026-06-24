package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.product.entity.CategoryBrandRelationEntity;
import com.yuebing.ybmall.product.mapper.CategoryBrandRelationMapper;
import com.yuebing.ybmall.product.service.CategoryBrandRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 品牌分类关联业务实现。
 *
 * <p>负责品牌分类关联查询，以及关系表中冗余名称字段的同步维护。</p>
 */
@Service
public class CategoryBrandRelationServiceImpl
        extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelationEntity>
        implements CategoryBrandRelationService {

    /**
     * 查询指定分类下关联的品牌列表。
     *
     * <p>这里直接查询关系表，因为关系表中已经冗余保存了品牌名称和分类名称。</p>
     *
     * @param catId 分类 ID
     * @return 品牌分类关联列表
     */
    @Override
    public List<CategoryBrandRelationEntity> getBrandsByCatId(Long catId) {
        LambdaQueryWrapper<CategoryBrandRelationEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getCatelogId, catId)
                .orderByAsc(CategoryBrandRelationEntity::getId);

        return this.list(wrapper);
    }

    /**
     * 同步更新关系表中的品牌名称冗余字段。
     *
     * @param brandId 品牌 ID
     * @param brandName 最新品牌名称
     */
    @Override
    public void updateBrandName(Long brandId, String brandName) {
        LambdaUpdateWrapper<CategoryBrandRelationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getBrandId, brandId)
                .set(CategoryBrandRelationEntity::getBrandName, brandName);

        this.update(wrapper);
    }

    /**
     * 同步更新关系表中的分类名称冗余字段。
     *
     * @param catId 分类 ID
     * @param catelogName 最新分类名称
     */
    @Override
    public void updateCategoryName(Long catId, String catelogName) {
        LambdaUpdateWrapper<CategoryBrandRelationEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CategoryBrandRelationEntity::getCatelogId, catId)
                .set(CategoryBrandRelationEntity::getCatelogName, catelogName);

        this.update(wrapper);
    }
}
