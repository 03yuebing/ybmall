package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 更新商品属性请求参数。
 *
 * <p>更新操作必须携带属性 ID，其余字段按业务需要选择性传入。</p>
 */
@Getter
@Setter
public class AttrUpdateVo {

    /**
     * 属性 ID。
     */
    @NotNull(message = "属性id不能为空")
    private Long attrId;

    /**
     * 属性名称。
     */
    private String attrName;

    /**
     * 是否需要检索：0 表示不需要，1 表示需要。
     */
    @Min(value = 0, message = "是否需要检索只能是0或1")
    @Max(value = 1, message = "是否需要检索只能是0或1")
    private Integer searchType;

    /**
     * 值类型：0 表示单个值，1 表示可以选择多个值。
     */
    @Min(value = 0, message = "值类型只能是0或1")
    @Max(value = 1, message = "值类型只能是0或1")
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
    @Min(value = 0, message = "属性类型只能是0或1")
    @Max(value = 1, message = "属性类型只能是0或1")
    private Integer attrType;

    /**
     * 启用状态：0 表示禁用，1 表示启用。
     */
    @Min(value = 0, message = "启用状态只能是0或1")
    @Max(value = 1, message = "启用状态只能是0或1")
    private Long enable;

    /**
     * 所属分类 ID。
     */
    private Long catelogId;

    /**
     * 是否快速展示：0 表示否，1 表示是。
     */
    @Min(value = 0, message = "快速展示状态只能是0或1")
    @Max(value = 1, message = "快速展示状态只能是0或1")
    private Integer showDesc;

    /**
     * 属性分组 ID。
     *
     * <p>基本属性/规格参数需要维护属性分组关系；销售属性不需要。</p>
     */
    private Long attrGroupId;
}
