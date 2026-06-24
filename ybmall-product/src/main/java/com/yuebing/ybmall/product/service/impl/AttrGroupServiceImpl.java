package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.exception.BizException;
import com.yuebing.ybmall.product.entity.AttrGroupEntity;
import com.yuebing.ybmall.product.mapper.AttrGroupMapper;
import com.yuebing.ybmall.product.service.AttrAttrgroupRelationService;
import com.yuebing.ybmall.product.service.AttrGroupService;
import com.yuebing.ybmall.product.vo.AttrGroupSaveVo;
import com.yuebing.ybmall.product.vo.AttrGroupUpdateVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 属性分组业务实现。
 *
 * <p>负责按照分类、关键字和分页参数查询属性分组列表。</p>
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroupEntity>
        implements AttrGroupService {

    private static final long ALL_CATELOG_ID = 0L;

    private final AttrAttrgroupRelationService attrAttrgroupRelationService;

    public AttrGroupServiceImpl(AttrAttrgroupRelationService attrAttrgroupRelationService) {
        this.attrAttrgroupRelationService = attrAttrgroupRelationService;
    }

    /**
     * 分页查询属性分组。
     *
     * <p>当 {@code catelogId} 为 0 时查询全部属性分组；否则只查询指定分类下的属性分组。
     * 当 {@code key} 可以转换为数字时，同时按分组 ID 和分组名称查询；
     * 否则只按分组名称模糊查询。</p>
     *
     * @param page 当前页码
     * @param limit 每页记录数
     * @param key 搜索关键字
     * @param catelogId 分类 ID
     * @return 分页结果
     */
    @Override
    public Page<AttrGroupEntity> queryPage(Long page, Long limit, String key, Long catelogId) {
        Page<AttrGroupEntity> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<AttrGroupEntity> wrapper = new LambdaQueryWrapper<>();

        if (catelogId != null && catelogId != ALL_CATELOG_ID) {
            wrapper.eq(AttrGroupEntity::getCatelogId, catelogId);
        }
        
        //StringUtils.hasText(key) 表示 key 不是空字符串，也不是全空格
        if (StringUtils.hasText(key)) {
            Long attrGroupId = tryParseLong(key);

            if (attrGroupId != null) {
                wrapper.and(query -> query
                        .eq(AttrGroupEntity::getAttrGroupId, attrGroupId)
                        .or()
                        .like(AttrGroupEntity::getAttrGroupName, key)
                );
            } else {
                wrapper.like(AttrGroupEntity::getAttrGroupName, key);
            }
        }

        wrapper.orderByAsc(AttrGroupEntity::getSort)
                .orderByAsc(AttrGroupEntity::getAttrGroupId);

        return this.page(pageParam, wrapper);
    }

    /**
     * 新增属性分组。
     *
     * @param attrGroupSaveVo 新增属性分组请求参数
     */
    @Override
    public void saveAttrGroup(AttrGroupSaveVo attrGroupSaveVo) {
        AttrGroupEntity attrGroup = new AttrGroupEntity();
        attrGroup.setAttrGroupName(attrGroupSaveVo.getAttrGroupName());
        attrGroup.setSort(attrGroupSaveVo.getSort());
        attrGroup.setDescript(attrGroupSaveVo.getDescript());
        attrGroup.setIcon(attrGroupSaveVo.getIcon());
        attrGroup.setCatelogId(attrGroupSaveVo.getCatelogId());

        this.save(attrGroup);
    }

    /**
     * 根据属性分组 ID 查询详情。
     *
     * @param attrGroupId 属性分组 ID
     * @return 属性分组详情
     */
    @Override
    public AttrGroupEntity getAttrGroupById(Long attrGroupId) {
        AttrGroupEntity attrGroup = this.getById(attrGroupId);

        if (attrGroup == null) {
            throw new BizException(BizCodeEnum.PRODUCT_ATTR_GROUP_NOT_FOUND);
        }

        return attrGroup;
    }

    /**
     * 更新属性分组。
     *
     * @param attrGroupUpdateVo 更新属性分组请求参数
     */
    @Override
    public void updateAttrGroup(AttrGroupUpdateVo attrGroupUpdateVo) {
        AttrGroupEntity attrGroup = this.getById(attrGroupUpdateVo.getAttrGroupId());

        if (attrGroup == null) {
            throw new BizException(BizCodeEnum.PRODUCT_ATTR_GROUP_NOT_FOUND);
        }

        AttrGroupEntity updateAttrGroup = new AttrGroupEntity();
        updateAttrGroup.setAttrGroupId(attrGroupUpdateVo.getAttrGroupId());
        updateAttrGroup.setAttrGroupName(attrGroupUpdateVo.getAttrGroupName());
        updateAttrGroup.setSort(attrGroupUpdateVo.getSort());
        updateAttrGroup.setDescript(attrGroupUpdateVo.getDescript());
        updateAttrGroup.setIcon(attrGroupUpdateVo.getIcon());
        updateAttrGroup.setCatelogId(attrGroupUpdateVo.getCatelogId());

        this.updateById(updateAttrGroup);
    }

    /**
     * 批量删除属性分组。
     *
     * <p>如果属性分组下已经关联属性，则禁止删除，避免关系表出现悬空数据。</p>
     *
     * @param attrGroupIds 待删除的属性分组 ID 列表
     */
    @Override
    public void removeAttrGroupByIds(List<Long> attrGroupIds) {
        if (attrGroupIds == null || attrGroupIds.isEmpty()) {
            return;
        }

        for (Long attrGroupId : attrGroupIds) {
            boolean hasRelation = attrAttrgroupRelationService.hasRelationByAttrGroupId(attrGroupId);

            if (hasRelation) {
                throw new BizException(BizCodeEnum.PRODUCT_ATTR_GROUP_HAS_RELATION);
            }
        }

        this.removeByIds(attrGroupIds);
    }

    /**
     * 尝试把字符串转换为 Long。
     *
     * <p>搜索关键字不一定是数字，转换失败时返回 null，避免数据库做隐式类型转换。</p>
     *
     * @param value 待转换字符串
     * @return 转换成功后的 Long；转换失败返回 null
     */
    private Long tryParseLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
}
