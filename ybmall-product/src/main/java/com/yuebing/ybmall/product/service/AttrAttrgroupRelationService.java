package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.AttrAttrgroupRelationEntity;

/**
 * 属性与属性分组关联业务接口。
 *
 * <p>封装属性分组删除前的关联关系检查能力。</p>
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    /**
     * 判断属性分组下是否已经关联属性。
     *
     * @param attrGroupId 属性分组 ID
     * @return true 表示存在关联属性，false 表示没有关联属性
     */
    boolean hasRelationByAttrGroupId(Long attrGroupId);
}
