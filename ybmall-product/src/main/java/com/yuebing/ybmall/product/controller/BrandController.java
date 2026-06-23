package com.yuebing.ybmall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuebing.ybmall.common.utils.R;
import com.yuebing.ybmall.product.entity.BrandEntity;
import com.yuebing.ybmall.product.service.BrandService;
import com.yuebing.ybmall.product.vo.BrandSaveVo;
import com.yuebing.ybmall.product.vo.BrandUpdateVo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 品牌管理接口。
 *
 * <p>提供后台管理系统使用的品牌分页查询、新增、详情、更新和批量删除能力。</p>
 */
@RestController
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * 分页查询品牌列表。
     *
     * @param page 当前页码，从 1 开始
     * @param limit 每页记录数
     * @param key 搜索关键字，可匹配品牌 ID 或品牌名称
     * @return 分页品牌数据
     */
    @GetMapping("/product/brand/list")
    public R list(@RequestParam(defaultValue = "1") Long page,
                  @RequestParam(defaultValue = "10") Long limit,
                  @RequestParam(required = false) String key) {
        Page<BrandEntity> result = brandService.queryPage(page, limit, key);
        return R.ok().put("data", result);
    }

    /**
     * 新增品牌。
     *
     * @param brandSaveVo 新增品牌请求参数
     * @return 操作结果
     */
    @PostMapping("/product/brand/save")
    public R save(@Valid @RequestBody BrandSaveVo brandSaveVo) {
        brandService.saveBrand(brandSaveVo);
        return R.ok();
    }

    /**
     * 查询品牌详情。
     *
     * @param brandId 品牌 ID
     * @return 品牌详情
     */
    @GetMapping("/product/brand/info/{brandId}")
    public R info(@PathVariable Long brandId) {
        BrandEntity brand = brandService.getBrandById(brandId);
        return R.ok().put("data", brand);
    }

    /**
     * 更新品牌。
     *
     * @param brandUpdateVo 更新品牌请求参数
     * @return 操作结果
     */
    @PostMapping("/product/brand/update")
    public R update(@Valid @RequestBody BrandUpdateVo brandUpdateVo) {
        brandService.updateBrand(brandUpdateVo);
        return R.ok();
    }

    /**
     * 批量删除品牌。
     *
     * @param brandIds 待删除的品牌 ID 列表
     * @return 操作结果
     */
    @PostMapping("/product/brand/delete")
    public R delete(@RequestBody List<Long> brandIds) {
        brandService.removeBrandByIds(brandIds);
        return R.ok();
    }
}
