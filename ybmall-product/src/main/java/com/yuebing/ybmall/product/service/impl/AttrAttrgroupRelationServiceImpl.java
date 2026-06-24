package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.product.entity.AttrAttrgroupRelationEntity;
import com.yuebing.ybmall.product.mapper.AttrAttrgroupRelationMapper;
import com.yuebing.ybmall.product.service.AttrAttrgroupRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 根据属性 ID 查询属性分组关联关系。
     *
     * @param attrId 属性 ID
     * @return 关联关系；不存在时返回 null
     */
    @Override
    public AttrAttrgroupRelationEntity getByAttrId(Long attrId) {
        return this.lambdaQuery()
                .eq(AttrAttrgroupRelationEntity::getAttrId, attrId)
                .one();
    }

    /**
     * 保存或更新属性和属性分组的关联关系。
     *
     * @param attrId 属性 ID
     * @param attrGroupId 属性分组 ID
     */
    @Override
    public void saveOrUpdateRelation(Long attrId, Long attrGroupId) {
        AttrAttrgroupRelationEntity relation = getByAttrId(attrId);

        if (relation == null) {
            relation = new AttrAttrgroupRelationEntity();
            relation.setAttrId(attrId);
            relation.setAttrGroupId(attrGroupId);
            this.save(relation);
            return;
        }

        relation.setAttrGroupId(attrGroupId);
        this.updateById(relation);
    }

    /**
     * 根据属性 ID 删除属性分组关联关系。
     *
     * @param attrId 属性 ID
     */
    @Override
    public void removeByAttrId(Long attrId) {
        this.lambdaUpdate()
                .eq(AttrAttrgroupRelationEntity::getAttrId, attrId)
                .remove();
    }

    /**
     * 根据属性 ID 批量删除属性分组关联关系。
     *
     * @param attrIds 属性 ID 列表
     */
    @Override
    public void removeByAttrIds(List<Long> attrIds) {
        if (attrIds == null || attrIds.isEmpty()) {
            return;
        }

        this.lambdaUpdate()
                .in(AttrAttrgroupRelationEntity::getAttrId, attrIds)
                .remove();
    }
}
