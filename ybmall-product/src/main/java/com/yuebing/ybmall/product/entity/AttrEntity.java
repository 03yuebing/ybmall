package com.yuebing.ybmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商品属性实体。
 *
 * <p>对应数据库表 {@code pms_attr}，用于维护某个分类下可填写的属性定义。
 * 属性分为基本属性/规格参数和销售属性。</p>
 */
@Getter
@Setter
@TableName("pms_attr")
public class AttrEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性 ID。
     */
    @TableId
    private Long attrId;

    /**
     * 属性名称。
     */
    private String attrName;

    /**
     * 是否需要检索：0 表示不需要，1 表示需要。
     */
    private Integer searchType;

    /**
     * 值类型：0 表示单个值，1 表示可以选择多个值。
     */
    private Integer valueType;

    /**
     * 属性图标。
     */
    private String icon;

    /**
     * 可选值列表，多个值通常用分号分隔。
     */
    private String valueSelect;

    /**
     * 属性类型：0 表示销售属性，1 表示基本属性/规格参数。
     */
    private Integer attrType;

    /**
     * 启用状态：0 表示禁用，1 表示启用。
     */
    private Long enable;

    /**
     * 所属分类 ID。
     *
     * <p>注意：课程数据库字段名是 {@code catelog_id}，不是 {@code catalog_id}。</p>
     */
    private Long catelogId;

    /**
     * 是否快速展示：0 表示否，1 表示是。
     */
    private Integer showDesc;
}
