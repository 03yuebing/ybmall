package com.yuebing.ybmall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuebing.ybmall.common.utils.R;
import com.yuebing.ybmall.product.service.AttrService;
import com.yuebing.ybmall.product.vo.AttrInfoVo;
import com.yuebing.ybmall.product.vo.AttrSaveVo;
import com.yuebing.ybmall.product.vo.AttrUpdateVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品属性管理接口。
 *
 * <p>提供规格参数和销售属性的管理能力。</p>
 */
@RestController
public class AttrController {

    private final AttrService attrService;

    public AttrController(AttrService attrService) {
        this.attrService = attrService;
    }

    /**
     * 新增商品属性。
     *
     * @param attrSaveVo 新增属性请求参数
     * @return 操作结果
     */
    @PostMapping("/product/attr/save")
    public R save(@Valid @RequestBody AttrSaveVo attrSaveVo) {
        attrService.saveAttr(attrSaveVo);
        return R.ok();
    }

    /**
     * 分页查询规格参数或销售属性。
     *
     * @param attrType 属性类型，base 表示规格参数，sale 表示销售属性
     * @param catelogId 分类 ID；为 0 时查询全部分类下的属性
     * @param page 当前页码，从 1 开始
     * @param limit 每页记录数
     * @param key 搜索关键字，可匹配属性 ID 或属性名称
     * @return 分页属性数据
     */
    @GetMapping("/product/attr/{attrType}/list/{catelogId}")
    public R list(@PathVariable String attrType,
                  @PathVariable Long catelogId,
                  @RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  @RequestParam(required = false) String key) {
        Page<AttrInfoVo> result = attrService.queryPage(page, limit, key, catelogId, attrType);
        return R.ok().put("data", result);
    }

    /**
     * 查询属性详情。
     *
     * @param attrId 属性 ID
     * @return 属性详情
     */
    @GetMapping("/product/attr/info/{attrId}")
    public R info(@PathVariable Long attrId) {
        AttrInfoVo attr = attrService.getAttrInfo(attrId);
        return R.ok().put("data", attr);
    }

    /**
     * 更新商品属性。
     *
     * @param attrUpdateVo 更新属性请求参数
     * @return 操作结果
     */
    @PostMapping("/product/attr/update")
    public R update(@Valid @RequestBody AttrUpdateVo attrUpdateVo) {
        attrService.updateAttr(attrUpdateVo);
        return R.ok();
    }

    /**
     * 批量删除商品属性。
     *
     * @param attrIds 待删除的属性 ID 列表
     * @return 操作结果
     */
    @PostMapping("/product/attr/delete")
    public R delete(@RequestBody List<Long> attrIds) {
        attrService.removeAttrByIds(attrIds);
        return R.ok();
    }
}
