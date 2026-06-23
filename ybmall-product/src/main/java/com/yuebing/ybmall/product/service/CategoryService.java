package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.vo.CategorySaveVo;
import com.yuebing.ybmall.product.vo.CategoryUpdateVo;

import java.util.List;

/**
 * 商品分类业务接口。
 *
 * <p>封装分类树构建、分类新增、详情、更新和删除等业务能力，
 * Controller 通过该接口访问分类业务，不直接操作 Mapper。</p>
 */
public interface CategoryService extends IService<CategoryEntity> {

    /**
     * 查询商品三级分类树。
     *
     * @return 三级分类树
     */
    List<CategoryEntity> listWithTree();

    /**
     * 新增商品分类。
     *
     * @param categorySaveVo 新增分类请求参数
     */
    void saveCategory(CategorySaveVo categorySaveVo);

    /**
     * 根据分类 ID 查询详情。
     *
     * @param catId 分类 ID
     * @return 分类详情
     */
    CategoryEntity getCategoryById(Long catId);

    /**
     * 更新商品分类。
     *
     * @param categoryUpdateVo 更新分类请求参数
     */
    void updateCategory(CategoryUpdateVo categoryUpdateVo);

    /**
     * 批量逻辑删除商品分类。
     *
     * @param catIds 待删除的分类 ID 列表
     */
    void removeCategoryByIds(List<Long> catIds);
}
