package com.yuebing.ybmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 属性与属性分组关联实体。
 *
 * <p>对应数据库表 {@code pms_attr_attrgroup_relation}，
 * 用来维护规格参数和属性分组之间的关联关系。</p>
 */
@Getter
@Setter
@TableName("pms_attr_attrgroup_relation")
public class AttrAttrgroupRelationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关系记录 ID。
     */
    @TableId
    private Long id;

    /**
     * 属性 ID，对应 {@code pms_attr.attr_id}。
     */
    private Long attrId;

    /**
     * 属性分组 ID，对应 {@code pms_attr_group.attr_group_id}。
     */
    private Long attrGroupId;

    /**
     * 属性在分组内的排序值。
     */
    private Integer attrSort;
}
