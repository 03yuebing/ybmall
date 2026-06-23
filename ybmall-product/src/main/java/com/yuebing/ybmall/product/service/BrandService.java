package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.BrandEntity;
import com.yuebing.ybmall.product.vo.BrandSaveVo;
import com.yuebing.ybmall.product.vo.BrandUpdateVo;

import java.util.List;

/**
 * 品牌业务接口。
 *
 * <p>封装品牌分页查询和基础 CRUD 能力。</p>
 */
public interface BrandService extends IService<BrandEntity> {

    /**
     * 分页查询品牌列表。
     *
     * @param page 当前页码
     * @param limit 每页记录数
     * @param key 搜索关键字，可匹配品牌 ID 或品牌名称
     * @return 分页结果
     */
    Page<BrandEntity> queryPage(Long page, Long limit, String key);

    /**
     * 新增品牌。
     *
     * @param brandSaveVo 新增品牌请求参数
     */
    void saveBrand(BrandSaveVo brandSaveVo);

    /**
     * 根据品牌 ID 查询详情。
     *
     * @param brandId 品牌 ID
     * @return 品牌详情
     */
    BrandEntity getBrandById(Long brandId);

    /**
     * 更新品牌。
     *
     * @param brandUpdateVo 更新品牌请求参数
     */
    void updateBrand(BrandUpdateVo brandUpdateVo);

    /**
     * 批量删除品牌。
     *
     * @param brandIds 待删除的品牌 ID 列表
     */
    void removeBrandByIds(List<Long> brandIds);
}
