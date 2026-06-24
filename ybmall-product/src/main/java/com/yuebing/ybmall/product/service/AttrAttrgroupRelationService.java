package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.AttrAttrgroupRelationEntity;

import java.util.List;

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

    /**
     * 根据属性 ID 查询属性分组关联关系。
     *
     * @param attrId 属性 ID
     * @return 关联关系；不存在时返回 null
     */
    AttrAttrgroupRelationEntity getByAttrId(Long attrId);

    /**
     * 保存或更新属性和属性分组的关联关系。
     *
     * @param attrId 属性 ID
     * @param attrGroupId 属性分组 ID
     */
    void saveOrUpdateRelation(Long attrId, Long attrGroupId);

    /**
     * 根据属性 ID 删除属性分组关联关系。
     *
     * @param attrId 属性 ID
     */
    void removeByAttrId(Long attrId);

    /**
     * 根据属性 ID 批量删除属性分组关联关系。
     *
     * @param attrIds 属性 ID 列表
     */
    void removeByAttrIds(List<Long> attrIds);
}
