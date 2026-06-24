package com.yuebing.ybmall.product.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 商品属性详情响应对象。
 *
 * <p>除了属性表本身字段，还补充分类名称、属性分组 ID 和属性分组名称，
 * 方便后台页面列表展示和编辑回显。</p>
 */
@Getter
@Setter
public class AttrInfoVo {

    private Long attrId;

    private String attrName;

    private Integer searchType;

    private Integer valueType;

    private String icon;

    private String valueSelect;

    private Integer attrType;

    private Long enable;

    private Long catelogId;

    private Integer showDesc;

    private Long attrGroupId;

    private String groupName;

    private String catelogName;
}
