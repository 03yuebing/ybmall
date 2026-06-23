package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

/**
 * 更新品牌请求参数。
 *
 * <p>更新操作必须携带品牌 ID，其余字段按业务需要选择性传入。</p>
 */
@Getter
@Setter
public class BrandUpdateVo {

    /**
     * 品牌 ID。
     */
    @NotNull(message = "品牌id不能为空")
    private Long brandId;

    /**
     * 品牌名称。
     */
    private String name;

    /**
     * 品牌 Logo 地址。
     */
    @URL(message = "品牌logo必须是合法的URL地址")
    private String logo;

    /**
     * 品牌介绍。
     */
    private String descript;

    /**
     * 显示状态：0 表示不显示，1 表示显示。
     */
    @Min(value = 0, message = "显示状态只能是0或1")
    @Max(value = 1, message = "显示状态只能是0或1")
    private Integer showStatus;

    /**
     * 检索首字母。
     */
    private String firstLetter;

    /**
     * 排序值。
     */
    private Integer sort;
}
