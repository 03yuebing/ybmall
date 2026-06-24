package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.exception.BizException;
import com.yuebing.ybmall.product.entity.AttrAttrgroupRelationEntity;
import com.yuebing.ybmall.product.mapper.AttrAttrgroupRelationMapper;
import com.yuebing.ybmall.product.service.AttrAttrgroupRelationService;
import com.yuebing.ybmall.product.vo.AttrAttrgroupRelationVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /**
     * 批量新增属性和属性分组的关联关系。
     *
     * <p>同一个属性只能关联一个属性分组。如果属性已经在关系表中存在，
     * 或同一次请求中重复提交同一个属性，则禁止新增。</p>
     *
     * @param relationVos 关联关系请求列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRelations(List<AttrAttrgroupRelationVo> relationVos) {
        if (relationVos == null || relationVos.isEmpty()) {
            return;
        }

        Set<Long> requestAttrIds = new HashSet<>();
        List<AttrAttrgroupRelationEntity> relations = new ArrayList<>();

        for (AttrAttrgroupRelationVo relationVo : relationVos) {
            if (!requestAttrIds.add(relationVo.getAttrId()) || getByAttrId(relationVo.getAttrId()) != null) {
                throw new BizException(BizCodeEnum.PRODUCT_ATTR_HAS_RELATION);
            }

            AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
            relation.setAttrId(relationVo.getAttrId());
            relation.setAttrGroupId(relationVo.getAttrGroupId());
            relations.add(relation);
        }

        this.saveBatch(relations);
    }

    /**
     * 批量删除属性和属性分组的关联关系。
     *
     * @param relationVos 关联关系请求列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRelations(List<AttrAttrgroupRelationVo> relationVos) {
        if (relationVos == null || relationVos.isEmpty()) {
            return;
        }

        for (AttrAttrgroupRelationVo relationVo : relationVos) {
            this.lambdaUpdate()
                    .eq(AttrAttrgroupRelationEntity::getAttrId, relationVo.getAttrId())
                    .eq(AttrAttrgroupRelationEntity::getAttrGroupId, relationVo.getAttrGroupId())
                    .remove();
        }
    }
}
