package com.yuebing.ybmall.common.exception;

public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    PRODUCT_CATEGORY_NOT_FOUND(11000, "分类不存在"),
    PRODUCT_CATEGORY_HAS_CHILDREN(11001, "当前分类存在子分类，不能删除");


    private final int code;
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