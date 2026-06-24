package com.yuebing.ybmall.common.exception;

/**
 * 业务错误码枚举。
 *
 * <p>统一维护后端对前端返回的业务错误码和错误信息，避免在 Controller
 * 或 Service 中散落硬编码的 code/msg。</p>
 */
public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    PRODUCT_CATEGORY_NOT_FOUND(11000, "分类不存在"),
    PRODUCT_CATEGORY_HAS_CHILDREN(11001, "当前分类存在子分类，不能删除"),
    PRODUCT_BRAND_NOT_FOUND(11100, "品牌不存在"),
    PRODUCT_ATTR_GROUP_NOT_FOUND(11200, "属性分组不存在"),
    PRODUCT_ATTR_GROUP_HAS_RELATION(11201, "当前属性分组存在关联属性，不能删除"),
    PRODUCT_ATTR_GROUP_REQUIRED(11300, "规格参数必须关联属性分组"),
    PRODUCT_ATTR_NOT_FOUND(11301, "属性不存在"),
    PRODUCT_ATTR_TYPE_INVALID(11302, "属性类型不合法"),
    PRODUCT_ATTR_HAS_RELATION(11303, "属性已经关联属性分组，不能重复关联");

    /**
     * 业务错误码。
     */
    private final int code;

    /**
     * 面向前端或用户展示的错误提示。
     */
    private final String msg;

    BizCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
