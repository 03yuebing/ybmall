package com.yuebing.ybmall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuebing.ybmall.common.utils.R;
import com.yuebing.ybmall.product.entity.AttrEntity;
import com.yuebing.ybmall.product.entity.AttrGroupEntity;
import com.yuebing.ybmall.product.service.AttrAttrgroupRelationService;
import com.yuebing.ybmall.product.service.AttrGroupService;
import com.yuebing.ybmall.product.vo.AttrAttrgroupRelationVo;
import com.yuebing.ybmall.product.vo.AttrGroupSaveVo;
import com.yuebing.ybmall.product.vo.AttrGroupUpdateVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 属性分组管理接口。
 *
 * <p>提供后台管理系统使用的属性分组分页查询能力。</p>
 */
@RestController
public class AttrGroupController {

    private final AttrGroupService attrGroupService;
    private final AttrAttrgroupRelationService attrAttrgroupRelationService;

    public AttrGroupController(AttrGroupService attrGroupService,
                               AttrAttrgroupRelationService attrAttrgroupRelationService) {
        this.attrGroupService = attrGroupService;
        this.attrAttrgroupRelationService = attrAttrgroupRelationService;
    }

    /**
     * 分页查询属性分组列表。
     *
     * @param catelogId 分类 ID；为 0 时查询全部分类下的属性分组
     * @param page 当前页码，从 1 开始
     * @param limit 每页记录数
     * @param key 搜索关键字，可匹配分组 ID 或分组名称
     * @return 分页属性分组数据
     */
    @GetMapping("/product/attrgroup/list/{catelogId}")
    public R list(@PathVariable Long catelogId,
                  @RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  @RequestParam(required = false) String key) {
        Page<AttrGroupEntity> result = attrGroupService.queryPage(page, limit, key, catelogId);
        return R.ok().put("data", result);
    }

    /**
     * 新增属性分组。
     *
     * @param attrGroupSaveVo 新增属性分组请求参数
     * @return 操作结果
     */
    @PostMapping("/product/attrgroup/save")
    public R save(@Valid @RequestBody AttrGroupSaveVo attrGroupSaveVo) {
        attrGroupService.saveAttrGroup(attrGroupSaveVo);
        return R.ok();
    }

    /**
     * 查询属性分组详情。
     *
     * @param attrGroupId 属性分组 ID
     * @return 属性分组详情
     */
    @GetMapping("/product/attrgroup/info/{attrGroupId}")
    public R info(@PathVariable Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getAttrGroupById(attrGroupId);
        return R.ok().put("data", attrGroup);
    }

    /**
     * 更新属性分组。
     *
     * @param attrGroupUpdateVo 更新属性分组请求参数
     * @return 操作结果
     */
    @PostMapping("/product/attrgroup/update")
    public R update(@Valid @RequestBody AttrGroupUpdateVo attrGroupUpdateVo) {
        attrGroupService.updateAttrGroup(attrGroupUpdateVo);
        return R.ok();
    }

    /**
     * 批量删除属性分组。
     *
     * @param attrGroupIds 待删除的属性分组 ID 列表
     * @return 操作结果
     */
    @PostMapping("/product/attrgroup/delete")
    public R delete(@RequestBody List<Long> attrGroupIds) {
        attrGroupService.removeAttrGroupByIds(attrGroupIds);
        return R.ok();
    }

    /**
     * 查询属性分组已经关联的属性列表。
     *
     * @param attrGroupId 属性分组 ID
     * @return 已关联属性列表
     */
    @GetMapping("/product/attrgroup/{attrGroupId}/attr/relation")
    public R relation(@PathVariable Long attrGroupId) {
        List<AttrEntity> attrs = attrGroupService.getRelationAttrs(attrGroupId);
        return R.ok().put("data", attrs);
    }

    /**
     * 分页查询属性分组还可以关联的属性列表。
     *
     * @param attrGroupId 属性分组 ID
     * @param page 当前页码，从 1 开始
     * @param limit 每页记录数
     * @param key 搜索关键字，可匹配属性 ID 或属性名称
     * @return 可关联属性分页结果
     */
    @GetMapping("/product/attrgroup/{attrGroupId}/noattr/relation")
    public R noRelation(@PathVariable Long attrGroupId,
                        @RequestParam(defaultValue = "1") Long page,
                        @RequestParam(defaultValue = "10") Long limit,
                        @RequestParam(required = false) String key) {
        Page<AttrEntity> result = attrGroupService.getNoRelationAttrs(attrGroupId, page, limit, key);
        return R.ok().put("data", result);
    }

    /**
     * 批量新增属性和属性分组的关联关系。
     *
     * @param relationVos 关联关系请求列表
     * @return 操作结果
     */
    @PostMapping("/product/attrgroup/attr/relation")
    public R saveRelation(@Valid @RequestBody List<AttrAttrgroupRelationVo> relationVos) {
        attrAttrgroupRelationService.saveRelations(relationVos);
        return R.ok();
    }

    /**
     * 批量删除属性和属性分组的关联关系。
     *
     * @param relationVos 关联关系请求列表
     * @return 操作结果
     */
    @PostMapping("/product/attrgroup/attr/relation/delete")
    public R deleteRelation(@Valid @RequestBody List<AttrAttrgroupRelationVo> relationVos) {
        attrAttrgroupRelationService.removeRelations(relationVos);
        return R.ok();
    }
}
