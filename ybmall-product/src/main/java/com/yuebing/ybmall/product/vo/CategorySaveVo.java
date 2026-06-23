package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 新增商品分类请求参数。
 *
 * <p>VO 只暴露前端允许提交的字段，避免直接使用数据库实体作为入参。</p>
 */
@Getter
public class CategorySaveVo {

    /**
     * 分类名称。
     */
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /**
     * 父分类 ID，一级分类传 0。
     */
    @NotNull(message = "父分类id不能为空")
    private Long parentCid;

    /**
     * 分类层级，限制为 1 到 3。
     */
    @NotNull(message = "分类层级不能为空")
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
