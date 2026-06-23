package com.yuebing.ybmall.common.exception;

/**
 * 业务异常。
 *
 * <p>用于表达“请求格式正确，但业务规则不成立”的情况，例如分类不存在、
 * 当前分类存在子分类不能删除等。该异常由业务层抛出，再由全局异常处理器
 * 转换成统一的 {@code R.error(...)} 响应。</p>
 */
public class BizException extends RuntimeException {

    /**
     * 业务错误码。
     */
    private final int code;

    /**
     * 根据统一错误码枚举创建业务异常。
     *
     * @param bizCodeEnum 业务错误码枚举
     */
    public BizException(BizCodeEnum bizCodeEnum) {
        super(bizCodeEnum.getMsg());
        this.code = bizCodeEnum.getCode();
    }

    /**
     * 根据自定义错误码和错误提示创建业务异常。
     *
     * @param code 业务错误码
     * @param msg 错误提示
     */
    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
