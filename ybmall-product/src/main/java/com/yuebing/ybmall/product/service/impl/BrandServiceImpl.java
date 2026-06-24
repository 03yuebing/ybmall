package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.exception.BizException;
import com.yuebing.ybmall.product.entity.BrandEntity;
import com.yuebing.ybmall.product.mapper.BrandMapper;
import com.yuebing.ybmall.product.service.BrandService;
import com.yuebing.ybmall.product.service.CategoryBrandRelationService;
import com.yuebing.ybmall.product.vo.BrandSaveVo;
import com.yuebing.ybmall.product.vo.BrandUpdateVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 品牌业务实现。
 *
 * <p>负责品牌分页查询和基础 CRUD 操作。</p>
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, BrandEntity> implements BrandService {

    private final CategoryBrandRelationService categoryBrandRelationService;

    public BrandServiceImpl(CategoryBrandRelationService categoryBrandRelationService) {
        this.categoryBrandRelationService = categoryBrandRelationService;
    }

    /**
     * 分页查询品牌列表。
     *
     * <p>当 key 不为空时，同时支持按品牌 ID 精确匹配和品牌名称模糊匹配。</p>
     *
     * @param page 当前页码
     * @param limit 每页记录数
     * @param key 搜索关键字
     * @return 分页结果
     */
    @Override
    public Page<BrandEntity> queryPage(Long page, Long limit, String key) {
        Page<BrandEntity> pageParam = new Page<>(page, limit);

        LambdaQueryWrapper<BrandEntity> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(key)) {
            wrapper.and(query -> query
                    .eq(BrandEntity::getBrandId, key)
                    .or()
                    .like(BrandEntity::getName, key)
            );
        }

        wrapper.orderByAsc(BrandEntity::getSort);

        return this.page(pageParam, wrapper);
    }

    /**
     * 新增品牌。
     *
     * @param brandSaveVo 新增品牌请求参数
     */
    @Override
    public void saveBrand(BrandSaveVo brandSaveVo) {
        BrandEntity brand = new BrandEntity();
        brand.setName(brandSaveVo.getName());
        brand.setLogo(brandSaveVo.getLogo());
        brand.setDescript(brandSaveVo.getDescript());
        brand.setShowStatus(brandSaveVo.getShowStatus());
        brand.setFirstLetter(brandSaveVo.getFirstLetter());
        brand.setSort(brandSaveVo.getSort());

        this.save(brand);
    }

    /**
     * 根据品牌 ID 查询详情。
     *
     * @param brandId 品牌 ID
     * @return 品牌详情
     */
    @Override
    public BrandEntity getBrandById(Long brandId) {
        BrandEntity brand = this.getById(brandId);

        if (brand == null) {
            throw new BizException(BizCodeEnum.PRODUCT_BRAND_NOT_FOUND);
        }

        return brand;
    }

    /**
     * 更新品牌。
     *
     * @param brandUpdateVo 更新品牌请求参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBrand(BrandUpdateVo brandUpdateVo) {
        BrandEntity brand = this.getById(brandUpdateVo.getBrandId());

        if (brand == null) {
            throw new BizException(BizCodeEnum.PRODUCT_BRAND_NOT_FOUND);
        }

        BrandEntity updateBrand = new BrandEntity();
        updateBrand.setBrandId(brandUpdateVo.getBrandId());
        updateBrand.setName(brandUpdateVo.getName());
        updateBrand.setLogo(brandUpdateVo.getLogo());
        updateBrand.setDescript(brandUpdateVo.getDescript());
        updateBrand.setShowStatus(brandUpdateVo.getShowStatus());
        updateBrand.setFirstLetter(brandUpdateVo.getFirstLetter());
        updateBrand.setSort(brandUpdateVo.getSort());

        this.updateById(updateBrand);

        if (brandUpdateVo.getName() != null) {
            categoryBrandRelationService.updateBrandName(brandUpdateVo.getBrandId(), brandUpdateVo.getName());
        }
    }

    /**
     * 批量删除品牌。
     *
     * @param brandIds 待删除的品牌 ID 列表
     */
    @Override
    public void removeBrandByIds(List<Long> brandIds) {
        this.removeByIds(brandIds);
    }
}
