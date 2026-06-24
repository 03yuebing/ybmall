package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.CategoryBrandRelationEntity;

import java.util.List;

/**
 * 品牌分类关联业务接口。
 *
 * <p>封装品牌和分类之间的关联查询能力。</p>
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    /**
     * 查询指定分类下关联的品牌列表。
     *
     * @param catId 分类 ID，对应 {@code pms_category.cat_id}
     * @return 品牌分类关联列表
     */
    List<CategoryBrandRelationEntity> getBrandsByCatId(Long catId);

    /**
     * 同步更新关系表中的品牌名称冗余字段。
     *
     * <p>当 {@code pms_brand.name} 发生变化时，需要同步维护
     * {@code pms_category_brand_relation.brand_name}，避免后台展示数据不一致。</p>
     *
     * @param brandId 品牌 ID
     * @param brandName 最新品牌名称
     */
    void updateBrandName(Long brandId, String brandName);

    /**
     * 同步更新关系表中的分类名称冗余字段。
     *
     * <p>当 {@code pms_category.name} 发生变化时，需要同步维护
     * {@code pms_category_brand_relation.catelog_name}，避免后台展示数据不一致。</p>
     *
     * @param catId 分类 ID
     * @param catelogName 最新分类名称
     */
    void updateCategoryName(Long catId, String catelogName);
}
