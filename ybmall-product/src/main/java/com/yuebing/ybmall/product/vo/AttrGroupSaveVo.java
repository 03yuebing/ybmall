package com.yuebing.ybmall.product.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 新增属性分组请求参数。
 *
 * <p>用于接收后台管理系统新增属性分组时提交的数据。</p>
 */
@Getter
@Setter
public class AttrGroupSaveVo {

    /**
     * 属性分组名称。
     */
    @NotBlank(message = "属性分组名称不能为空")
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
    @NotNull(message = "所属分类id不能为空")
    private Long catelogId;
}
