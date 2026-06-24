package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 更新属性分组请求参数。
 *
 * <p>更新操作必须携带属性分组 ID，其余字段按业务需要选择性传入。</p>
 */
@Getter
@Setter
public class AttrGroupUpdateVo {

    /**
     * 属性分组 ID。
     */
    @NotNull(message = "属性分组id不能为空")
    private Long attrGroupId;

    /**
     * 属性分组名称。
     */
    private String attrGroupName;

    /**
     * 排序值。
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
     * 所属分类 ID。
     */
    private Long catelogId;
}
