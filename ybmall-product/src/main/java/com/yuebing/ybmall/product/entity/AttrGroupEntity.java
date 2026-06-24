package com.yuebing.ybmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 属性分组实体。
 *
 * <p>对应数据库表 {@code pms_attr_group}，用于维护某个分类下的规格参数分组，
 * 例如手机分类下的主体、基本信息、屏幕、主芯片等。</p>
 */
@Getter
@Setter
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 属性分组 ID。
     */
    @TableId
    private Long attrGroupId;

    /**
     * 属性分组名称。
     */
    private String attrGroupName;

    /**
     * 排序值，值越小越靠前。
     */
    private Integer sort;

    /**
     * 属性分组描述。
     */
    private String descript;

    /**
     * 属性分组图标。
     */
    private String icon;

    /**
     * 所属分类 ID，对应 {@code pms_category.cat_id}。
     *
     * <p>注意：课程数据库字段名是 {@code catelog_id}，不是 {@code catalog_id}。</p>
     */
    private Long catelogId;
}
