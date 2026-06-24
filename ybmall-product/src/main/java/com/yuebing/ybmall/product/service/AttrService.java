package com.yuebing.ybmall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuebing.ybmall.product.entity.AttrEntity;
import com.yuebing.ybmall.product.vo.AttrInfoVo;
import com.yuebing.ybmall.product.vo.AttrSaveVo;
import com.yuebing.ybmall.product.vo.AttrUpdateVo;

import java.util.List;

/**
 * 商品属性业务接口。
 *
 * <p>封装规格参数和销售属性的业务操作。</p>
 */
public interface AttrService extends IService<AttrEntity> {

    /**
     * 新增商品属性。
     *
     * <p>如果新增的是基本属性/规格参数，需要同时保存属性和属性分组的关联关系。</p>
     *
     * @param attrSaveVo 新增属性请求参数
     */
    void saveAttr(AttrSaveVo attrSaveVo);

    /**
     * 分页查询规格参数或销售属性。
     *
     * @param page 当前页码
     * @param limit 每页记录数
     * @param key 搜索关键字，可匹配属性 ID 或属性名称
     * @param catelogId 分类 ID；为 0 时查询全部分类下的属性
     * @param attrType 属性类型路径变量，base 表示规格参数，sale 表示销售属性
     * @return 分页结果
     */
    Page<AttrInfoVo> queryPage(Long page, Long limit, String key, Long catelogId, String attrType);

    /**
     * 根据属性 ID 查询详情。
     *
     * @param attrId 属性 ID
     * @return 属性详情
     */
    AttrInfoVo getAttrInfo(Long attrId);

    /**
     * 更新商品属性。
     *
     * @param attrUpdateVo 更新属性请求参数
     */
    void updateAttr(AttrUpdateVo attrUpdateVo);

    /**
     * 批量删除商品属性。
     *
     * @param attrIds 待删除的属性 ID 列表
     */
    void removeAttrByIds(List<Long> attrIds);
}
