package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.product.entity.AttrAttrgroupRelationEntity;
import com.yuebing.ybmall.product.mapper.AttrAttrgroupRelationMapper;
import com.yuebing.ybmall.product.service.AttrAttrgroupRelationService;
import org.springframework.stereotype.Service;

/**
 * 属性与属性分组关联业务实现。
 *
 * <p>当前主要用于属性分组删除前判断是否存在关联属性。</p>
 */
@Service
public class AttrAttrgroupRelationServiceImpl
        extends ServiceImpl<AttrAttrgroupRelationMapper, AttrAttrgroupRelationEntity>
        implements AttrAttrgroupRelationService {

    /**
     * 判断属性分组下是否已经关联属性。
     *
     * @param attrGroupId 属性分组 ID
     * @return true 表示存在关联属性，false 表示没有关联属性
     */
    @Override
    public boolean hasRelationByAttrGroupId(Long attrGroupId) {
        Long count = this.lambdaQuery()
                .eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId)
                .count();

        return count > 0;
    }
}
