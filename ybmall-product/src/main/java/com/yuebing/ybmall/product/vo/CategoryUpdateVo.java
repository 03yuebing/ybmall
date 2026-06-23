package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 更新商品分类请求参数。
 *
 * <p>更新操作必须携带分类 ID，其余字段按业务需要选择性传入。</p>
 */
@Getter
public class CategoryUpdateVo {

    /**
     * 分类 ID。
     */
    @NotNull(message = "分类id不能为空")
    private Long catId;

    /**
     * 分类名称。
     */
    private String name;

    /**
     * 父分类 ID。
     */
    private Long parentCid;

    /**
     * 分类层级，限制为 1 到 3。
     */
    @Min(value = 1, message = "分类层级最小为1")
    @Max(value = 3, message = "分类层级最大为3")
    private Integer catLevel;

    /**
     * 排序值。
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

}
