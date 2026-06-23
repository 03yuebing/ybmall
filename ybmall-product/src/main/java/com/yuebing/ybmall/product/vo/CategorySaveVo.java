package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategorySaveVo {

    @NotBlank(message = "分类名称不能为空")
    private String name;

    @NotNull(message = "父分类id不能为空")
    private Long parentCid;

    @NotNull(message = "分类层级不能为空")
    @Min(value = 1, message = "分类层级最小为1")
    @Max(value = 3, message = "分类层级最大为3")
    private Integer catLevel;

    private Integer sort;
    private String icon;
    private String productUnit;

    public String getName() {
        return name;
    }

    public Long getParentCid() {
        return parentCid;
    }

    public Integer getCatLevel() {
        return catLevel;
    }

    public Integer getSort() {
        return sort;
    }

    public String getIcon() {
        return icon;
    }

    public String getProductUnit() {
        return productUnit;
    }
}