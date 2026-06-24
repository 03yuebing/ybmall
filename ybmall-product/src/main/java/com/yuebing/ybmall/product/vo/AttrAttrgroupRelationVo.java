package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 属性与属性分组关联请求参数。
 *
 * <p>用于新增或删除属性和属性分组之间的中间表关系。</p>
 */
@Getter
@Setter
public class AttrAttrgroupRelationVo {

    /**
     * 属性 ID。
     */
    @NotNull(message = "属性id不能为空")
    private Long attrId;

    /**
     * 属性分组 ID。
     */
    @NotNull(message = "属性分组id不能为空")
    private Long attrGroupId;
}
