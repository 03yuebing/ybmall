package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.AttrEntity;
import com.yuebing.ybmall.product.entity.AttrGroupEntity;
import com.yuebing.ybmall.product.vo.AttrGroupSaveVo;
import com.yuebing.ybmall.product.vo.AttrGroupUpdateVo;

import java.util.List;

/**
 * 属性分组业务接口。
 *
 * <p>封装属性分组分页查询能力。</p>
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    /**
     * 分页查询属性分组。
     *
     * @param page 当前页码
     * @param limit 每页记录数
     * @param key 搜索关键字，可匹配分组 ID 或分组名称
     * @param catelogId 分类 ID；为 0 时查询全部分类下的属性分组
     * @return 分页结果
     */
    Page<AttrGroupEntity> queryPage(Long page, Long limit, String key, Long catelogId);

    /**
     * 新增属性分组。
     *
     * @param attrGroupSaveVo 新增属性分组请求参数
     */
    void saveAttrGroup(AttrGroupSaveVo attrGroupSaveVo);

    /**
     * 根据属性分组 ID 查询详情。
     *
     * @param attrGroupId 属性分组 ID
     * @return 属性分组详情
     */
    AttrGroupEntity getAttrGroupById(Long attrGroupId);

    /**
     * 更新属性分组。
     *
     * @param attrGroupUpdateVo 更新属性分组请求参数
     */
    void updateAttrGroup(AttrGroupUpdateVo attrGroupUpdateVo);

    /**
     * 批量删除属性分组。
     *
     * <p>删除前需要检查属性分组下是否仍有关联属性。</p>
     *
     * @param attrGroupIds 待删除的属性分组 ID 列表
     */
    void removeAttrGroupByIds(List<Long> attrGroupIds);

    /**
     * 查询属性分组已经关联的属性列表。
     *
     * @param attrGroupId 属性分组 ID
     * @return 已关联属性列表
     */
    List<AttrEntity> getRelationAttrs(Long attrGroupId);

    /**
     * 分页查询当前属性分组还可以关联的属性列表。
     *
     * <p>可关联属性 = 当前分类下所有基本属性 - 已经被任意属性分组关联过的属性。</p>
     *
     * @param attrGroupId 属性分组 ID
     * @param page 当前页码
     * @param limit 每页记录数
     * @param key 搜索关键字，可匹配属性 ID 或属性名称
     * @return 可关联属性分页结果
     */
    Page<AttrEntity> getNoRelationAttrs(Long attrGroupId, Long page, Long limit, String key);
}
