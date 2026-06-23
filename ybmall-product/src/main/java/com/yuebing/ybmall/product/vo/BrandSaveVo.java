package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

/**
 * 新增品牌请求参数。
 *
 * <p>用于接收后台管理端新增品牌时提交的数据。</p>
 */
@Getter
@Setter
public class BrandSaveVo {

    /**
     * 品牌名称。
     */
    @NotBlank(message = "品牌名称不能为空")
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
    @NotBlank(message = "检索首字母不能为空")
    private String firstLetter;

    /**
     * 排序值。
     */
    private Integer sort;
}
