package com.yuebing.ybmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 品牌分类关联实体。
 *
 * <p>对应数据库表 {@code pms_category_brand_relation}，
 * 用来维护品牌和三级分类之间的多对多关系。</p>
 */
@Getter
@Setter
@TableName("pms_category_brand_relation")
public class CategoryBrandRelationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关系记录 ID。
     */
    @TableId
    private Long id;

    /**
     * 品牌 ID，对应 {@code pms_brand.brand_id}。
     */
    private Long brandId;

    /**
     * 分类 ID，对应 {@code pms_category.cat_id}。
     *
     * <p>注意：课程数据库字段名是 {@code catelog_id}，不是 {@code catalog_id}。</p>
     */
    private Long catelogId;

    /**
     * 品牌名称冗余字段，用于减少列表展示时的表连接。
     */
    private String brandName;

    /**
     * 分类名称冗余字段，用于减少列表展示时的表连接。
     */
    private String catelogName;
}
