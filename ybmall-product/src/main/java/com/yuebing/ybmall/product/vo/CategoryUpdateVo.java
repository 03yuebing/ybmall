package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CategoryUpdateVo {

    @NotNull(message = "分类id不能为空")
    private Long catId;

    private String name;

    private Long parentCid;

    @Min(value = 1, message = "分类层级最小为1")
    @Max(value = 3, message = "分类层级最大为3")
    private Integer catLevel;

    private Integer sort;
    private String icon;
    private String productUnit;

    public Long getCatId() {
        return catId;
    }

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