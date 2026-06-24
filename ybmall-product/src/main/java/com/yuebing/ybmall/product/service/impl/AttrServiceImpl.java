package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.exception.BizException;
import com.yuebing.ybmall.product.entity.AttrAttrgroupRelationEntity;
import com.yuebing.ybmall.product.entity.AttrEntity;
import com.yuebing.ybmall.product.entity.AttrGroupEntity;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.mapper.AttrMapper;
import com.yuebing.ybmall.product.service.AttrAttrgroupRelationService;
import com.yuebing.ybmall.product.service.AttrGroupService;
import com.yuebing.ybmall.product.service.AttrService;
import com.yuebing.ybmall.product.service.CategoryService;
import com.yuebing.ybmall.product.vo.AttrInfoVo;
import com.yuebing.ybmall.product.vo.AttrSaveVo;
import com.yuebing.ybmall.product.vo.AttrUpdateVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品属性业务实现。
 *
 * <p>负责新增规格参数和销售属性。规格参数需要额外维护属性和属性分组的关系。</p>
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, AttrEntity> implements AttrService {

    /**
     * 基本属性/规格参数。
     */
    private static final int ATTR_TYPE_BASE = 1;

    /**
     * 销售属性。
     */
    private static final int ATTR_TYPE_SALE = 0;

    private static final long ALL_CATELOG_ID = 0L;

    private final AttrGroupService attrGroupService;
    private final AttrAttrgroupRelationService attrAttrgroupRelationService;
    private final CategoryService categoryService;

    public AttrServiceImpl(AttrGroupService attrGroupService,
                           AttrAttrgroupRelationService attrAttrgroupRelationService,
                           CategoryService categoryService) {
        this.attrGroupService = attrGroupService;
        this.attrAttrgroupRelationService = attrAttrgroupRelationService;
        this.categoryService = categoryService;
    }

    /**
     * 新增商品属性。
     *
     * <p>新增基本属性/规格参数时，同时保存 {@code pms_attr} 和
     * {@code pms_attr_attrgroup_relation}。两个写入动作放在同一个事务中。</p>
     *
     * @param attrSaveVo 新增属性请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAttr(AttrSaveVo attrSaveVo) {
        AttrEntity attr = new AttrEntity();
        attr.setAttrName(attrSaveVo.getAttrName());
        attr.setSearchType(attrSaveVo.getSearchType());
        attr.setValueType(attrSaveVo.getValueType());
        attr.setIcon(attrSaveVo.getIcon());
        attr.setValueSelect(attrSaveVo.getValueSelect());
        attr.setAttrType(attrSaveVo.getAttrType());
        attr.setEnable(attrSaveVo.getEnable());
        attr.setCatelogId(attrSaveVo.getCatelogId());
        attr.setShowDesc(attrSaveVo.getShowDesc());

        this.save(attr);

        if (isBaseAttr(attrSaveVo.getAttrType())) {
            saveAttrGroupRelation(attrSaveVo, attr);
        }
    }

    /**
     * 分页查询规格参数或销售属性。
     *
     * @param page 当前页码
     * @param limit 每页记录数
     * @param key 搜索关键字
     * @param catelogId 分类 ID
     * @param attrType 属性类型路径变量，base 表示规格参数，sale 表示销售属性
     * @return 分页结果
     */
    @Override
    public Page<AttrInfoVo> queryPage(Long page, Long limit, String key, Long catelogId, String attrType) {
        Integer attrTypeValue = parseAttrType(attrType);
        Page<AttrEntity> pageParam = new Page<>(page, limit);
        LambdaQueryWrapper<AttrEntity> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(AttrEntity::getAttrType, attrTypeValue);

        if (catelogId != null && catelogId != ALL_CATELOG_ID) {
            wrapper.eq(AttrEntity::getCatelogId, catelogId);
        }

        if (StringUtils.hasText(key)) {
            Long attrId = tryParseLong(key);

            if (attrId != null) {
                wrapper.and(query -> query
                        .eq(AttrEntity::getAttrId, attrId)
                        .or()
                        .like(AttrEntity::getAttrName, key)
                );
            } else {
                wrapper.like(AttrEntity::getAttrName, key);
            }
        }

        wrapper.orderByAsc(AttrEntity::getAttrId);

        Page<AttrEntity> attrPage = this.page(pageParam, wrapper);
        List<AttrInfoVo> records = attrPage.getRecords().stream()
                .map(attr -> buildAttrInfoVo(attr, isBaseAttr(attrTypeValue)))
                .toList();

        Page<AttrInfoVo> result = new Page<>(attrPage.getCurrent(), attrPage.getSize(), attrPage.getTotal());
        result.setRecords(records);

        return result;
    }

    /**
     * 根据属性 ID 查询详情。
     *
     * @param attrId 属性 ID
     * @return 属性详情
     */
    @Override
    public AttrInfoVo getAttrInfo(Long attrId) {
        AttrEntity attr = this.getById(attrId);

        if (attr == null) {
            throw new BizException(BizCodeEnum.PRODUCT_ATTR_NOT_FOUND);
        }

        return buildAttrInfoVo(attr, isBaseAttr(attr.getAttrType()));
    }

    /**
     * 更新商品属性。
     *
     * @param attrUpdateVo 更新属性请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAttr(AttrUpdateVo attrUpdateVo) {
        AttrEntity attr = this.getById(attrUpdateVo.getAttrId());

        if (attr == null) {
            throw new BizException(BizCodeEnum.PRODUCT_ATTR_NOT_FOUND);
        }

        AttrEntity updateAttr = new AttrEntity();
        updateAttr.setAttrId(attrUpdateVo.getAttrId());
        updateAttr.setAttrName(attrUpdateVo.getAttrName());
        updateAttr.setSearchType(attrUpdateVo.getSearchType());
        updateAttr.setValueType(attrUpdateVo.getValueType());
        updateAttr.setIcon(attrUpdateVo.getIcon());
        updateAttr.setValueSelect(attrUpdateVo.getValueSelect());
        updateAttr.setAttrType(attrUpdateVo.getAttrType());
        updateAttr.setEnable(attrUpdateVo.getEnable());
        updateAttr.setCatelogId(attrUpdateVo.getCatelogId());
        updateAttr.setShowDesc(attrUpdateVo.getShowDesc());

        this.updateById(updateAttr);

        Integer finalAttrType = attrUpdateVo.getAttrType() == null ? attr.getAttrType() : attrUpdateVo.getAttrType();
        syncAttrGroupRelation(attrUpdateVo, finalAttrType);
    }

    /**
     * 批量删除商品属性。
     *
     * @param attrIds 待删除的属性 ID 列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeAttrByIds(List<Long> attrIds) {
        if (attrIds == null || attrIds.isEmpty()) {
            return;
        }

        attrAttrgroupRelationService.removeByAttrIds(attrIds);
        this.removeByIds(attrIds);
    }

    /**
     * 保存规格参数和属性分组之间的关联关系。
     *
     * @param attrSaveVo 新增属性请求参数
     * @param attr 已经保存的属性实体
     */
    private void saveAttrGroupRelation(AttrSaveVo attrSaveVo, AttrEntity attr) {
        if (attrSaveVo.getAttrGroupId() == null) {
            throw new BizException(BizCodeEnum.PRODUCT_ATTR_GROUP_REQUIRED);
        }

        attrGroupService.getAttrGroupById(attrSaveVo.getAttrGroupId());

        AttrAttrgroupRelationEntity relation = new AttrAttrgroupRelationEntity();
        relation.setAttrId(attr.getAttrId());
        relation.setAttrGroupId(attrSaveVo.getAttrGroupId());

        attrAttrgroupRelationService.save(relation);
    }

    /**
     * 同步属性和属性分组之间的关联关系。
     *
     * @param attrUpdateVo 更新属性请求参数
     * @param finalAttrType 更新后的属性类型
     */
    private void syncAttrGroupRelation(AttrUpdateVo attrUpdateVo, Integer finalAttrType) {
        Long attrId = attrUpdateVo.getAttrId();

        if (!isBaseAttr(finalAttrType)) {
            attrAttrgroupRelationService.removeByAttrId(attrId);
            return;
        }

        if (attrUpdateVo.getAttrGroupId() != null) {
            attrGroupService.getAttrGroupById(attrUpdateVo.getAttrGroupId());
            attrAttrgroupRelationService.saveOrUpdateRelation(attrId, attrUpdateVo.getAttrGroupId());
            return;
        }

        AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService.getByAttrId(attrId);

        if (relation == null) {
            throw new BizException(BizCodeEnum.PRODUCT_ATTR_GROUP_REQUIRED);
        }
    }

    /**
     * 构建属性详情响应对象。
     *
     * @param attr 属性实体
     * @param includeGroupInfo 是否补充属性分组信息
     * @return 属性详情响应对象
     */
    private AttrInfoVo buildAttrInfoVo(AttrEntity attr, boolean includeGroupInfo) {
        AttrInfoVo infoVo = new AttrInfoVo();
        infoVo.setAttrId(attr.getAttrId());
        infoVo.setAttrName(attr.getAttrName());
        infoVo.setSearchType(attr.getSearchType());
        infoVo.setValueType(attr.getValueType());
        infoVo.setIcon(attr.getIcon());
        infoVo.setValueSelect(attr.getValueSelect());
        infoVo.setAttrType(attr.getAttrType());
        infoVo.setEnable(attr.getEnable());
        infoVo.setCatelogId(attr.getCatelogId());
        infoVo.setShowDesc(attr.getShowDesc());

        if (attr.getCatelogId() != null) {
            CategoryEntity category = categoryService.getById(attr.getCatelogId());

            if (category != null) {
                infoVo.setCatelogName(category.getName());
            }
        }

        if (includeGroupInfo) {
            fillAttrGroupInfo(infoVo, attr.getAttrId());
        }

        return infoVo;
    }

    /**
     * 补充规格参数关联的属性分组信息。
     *
     * @param infoVo 属性详情响应对象
     * @param attrId 属性 ID
     */
    private void fillAttrGroupInfo(AttrInfoVo infoVo, Long attrId) {
        AttrAttrgroupRelationEntity relation = attrAttrgroupRelationService.getByAttrId(attrId);

        if (relation == null || relation.getAttrGroupId() == null) {
            return;
        }

        infoVo.setAttrGroupId(relation.getAttrGroupId());

        AttrGroupEntity attrGroup = attrGroupService.getById(relation.getAttrGroupId());

        if (attrGroup != null) {
            infoVo.setGroupName(attrGroup.getAttrGroupName());
        }
    }

    /**
     * 根据路径变量解析属性类型。
     *
     * @param attrType 路径变量，base 或 sale
     * @return 数据库中的属性类型值
     */
    private Integer parseAttrType(String attrType) {
        if ("base".equalsIgnoreCase(attrType)) {
            return ATTR_TYPE_BASE;
        }

        if ("sale".equalsIgnoreCase(attrType)) {
            return ATTR_TYPE_SALE;
        }

        throw new BizException(BizCodeEnum.PRODUCT_ATTR_TYPE_INVALID);
    }

    /**
     * 判断是否为基本属性/规格参数。
     *
     * @param attrType 属性类型
     * @return true 表示基本属性/规格参数
     */
    private boolean isBaseAttr(Integer attrType) {
        return attrType != null && attrType == ATTR_TYPE_BASE;
    }

    /**
     * 尝试把字符串转换为 Long。
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
