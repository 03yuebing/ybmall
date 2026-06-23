package com.yuebing.ybmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 商品三级分类实体。
 *
 * <p>对应数据库表 {@code pms_category}，用于承载商品分类的基础字段以及
 * 返回分类树时使用的 children 子节点。</p>
 */
@Getter
@Setter
@TableName("pms_category")
public class CategoryEntity implements Serializable {

    /**
     * 分类 ID。
     */
    @TableId
    private Long catId;

    /**
     * 分类名称。
     */
    private String name;

    /**
     * 父分类 ID，一级分类的父 ID 为 0。
     */
    private Long parentCid;

    /**
     * 分类层级，课程数据中通常为 1、2、3。
     */
    private Integer catLevel;

    /**
     * 显示状态，同时作为逻辑删除字段：1 表示正常，0 表示已删除或不显示。
     */
    @TableLogic(value = "1", delval = "0")
    private Integer showStatus;

    /**
     * 排序值，值越小越靠前。
     */
    private Integer sort;

    /**
     * 分类图标地址。
     */
    private String icon;

    /**
     * 商品计量单位。
     */
    private String productUnit;

    /**
     * 分类下商品数量。
     */
    private Integer productCount;

    /**
     * 子分类列表，仅用于接口返回分类树，不对应数据库字段。
     */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<CategoryEntity> children;
}
