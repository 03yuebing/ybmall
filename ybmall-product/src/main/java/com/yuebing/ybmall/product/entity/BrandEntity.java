package com.yuebing.ybmall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 商品品牌实体。
 *
 * <p>对应数据库表 {@code pms_brand}，用于维护品牌名称、Logo、展示状态、
 * 检索首字母和排序等后台管理信息。</p>
 */
@Getter
@Setter
@TableName("pms_brand")
public class BrandEntity implements Serializable {

    /**
     * 品牌 ID。
     */
    @TableId
    private Long brandId;

    /**
     * 品牌名称。
     */
    private String name;

    /**
     * 品牌 Logo 地址。
     */
    private String logo;

    /**
     * 品牌介绍。
     */
    private String descript;

    /**
     * 显示状态：0 表示不显示，1 表示显示。
     */
    private Integer showStatus;

    /**
     * 检索首字母，例如 H、M、A。
     */
    private String firstLetter;

    /**
     * 排序值，值越小越靠前。
     */
    private Integer sort;
}
