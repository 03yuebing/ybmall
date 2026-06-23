package com.yuebing.ybmall.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.exception.BizException;
import com.yuebing.ybmall.product.entity.CategoryEntity;
import com.yuebing.ybmall.product.mapper.CategoryMapper;
import com.yuebing.ybmall.product.service.CategoryService;
import com.yuebing.ybmall.product.vo.CategorySaveVo;
import com.yuebing.ybmall.product.vo.CategoryUpdateVo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品分类业务实现。
 *
 * <p>负责分类树构建、分类增删改查以及删除前的业务规则校验。</p>
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements CategoryService {

    /**
     * 查询商品三级分类树。
     *
     * <p>先按照 parentCid 对全部分类分组，再递归挂载 children，避免递归过程中
     * 反复扫描全量分类数据。</p>
     *
     * @return 三级分类树
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> entities = baseMapper.selectList(null);

        Map<Long, List<CategoryEntity>> parentIdMap = entities.stream()
                .collect(Collectors.groupingBy(CategoryEntity::getParentCid));

        return parentIdMap.getOrDefault(0L, List.of()).stream()
                .peek(category -> category.setChildren(getChildren(category, parentIdMap)))
                .sorted(Comparator.comparingInt(category -> category.getSort() == null ? 0 : category.getSort()))
                .toList();
    }

    /**
     * 递归查询指定分类的子分类。
     *
     * @param root 当前父分类
     * @param parentIdMap 按父分类 ID 分组后的分类数据
     * @return 子分类列表
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, Map<Long, List<CategoryEntity>> parentIdMap) {
        return parentIdMap.getOrDefault(root.getCatId(), List.of()).stream()
                .peek(category -> category.setChildren(getChildren(category, parentIdMap)))
                .sorted(Comparator.comparingInt(category -> category.getSort() == null ? 0 : category.getSort()))
                .toList();
    }

    /**
     * 新增商品分类。
     *
     * @param categorySaveVo 新增分类请求参数
     */
    @Override
    public void saveCategory(CategorySaveVo categorySaveVo) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categorySaveVo.getName());
        categoryEntity.setParentCid(categorySaveVo.getParentCid());
        categoryEntity.setCatLevel(categorySaveVo.getCatLevel());
        categoryEntity.setSort(categorySaveVo.getSort());
        categoryEntity.setIcon(categorySaveVo.getIcon());
        categoryEntity.setProductUnit(categorySaveVo.getProductUnit());
        categoryEntity.setShowStatus(1);
        categoryEntity.setProductCount(0);

        this.save(categoryEntity);
    }

    /**
     * 根据分类 ID 查询详情。
     *
     * @param catId 分类 ID
     * @return 分类详情
     */
    @Override
    public CategoryEntity getCategoryById(Long catId) {
        CategoryEntity category = this.getById(catId);

        if (category == null) {
            throw new BizException(BizCodeEnum.PRODUCT_CATEGORY_NOT_FOUND);
        }

        return category;
    }

    /**
     * 更新商品分类。
     *
     * @param categoryUpdateVo 更新分类请求参数
     */
    @Override
    public void updateCategory(CategoryUpdateVo categoryUpdateVo) {
        CategoryEntity category = this.getById(categoryUpdateVo.getCatId());

        if (category == null) {
            throw new BizException(BizCodeEnum.PRODUCT_CATEGORY_NOT_FOUND);
        }

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCatId(categoryUpdateVo.getCatId());
        categoryEntity.setName(categoryUpdateVo.getName());
        categoryEntity.setParentCid(categoryUpdateVo.getParentCid());
        categoryEntity.setCatLevel(categoryUpdateVo.getCatLevel());
        categoryEntity.setSort(categoryUpdateVo.getSort());
        categoryEntity.setIcon(categoryUpdateVo.getIcon());
        categoryEntity.setProductUnit(categoryUpdateVo.getProductUnit());

        this.updateById(categoryEntity);
    }

    /**
     * 批量逻辑删除商品分类。
     *
     * <p>删除分类前先检查是否存在子分类，避免删除父分类后留下无法挂载的子节点。</p>
     *
     * @param catIds 待删除的分类 ID 列表
     */
    @Override
    public void removeCategoryByIds(List<Long> catIds) {
        for (Long catId : catIds) {
            long count = this.lambdaQuery()
                    .eq(CategoryEntity::getParentCid, catId)
                    .count();

            if (count > 0) {
                throw new BizException(BizCodeEnum.PRODUCT_CATEGORY_HAS_CHILDREN);
            }
        }

        this.removeByIds(catIds);
    }
}
