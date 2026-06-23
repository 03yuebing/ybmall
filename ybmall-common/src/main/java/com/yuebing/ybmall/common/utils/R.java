package com.yuebing.ybmall.common.utils;

import com.yuebing.ybmall.common.exception.BizCodeEnum;

import java.util.HashMap;

/**
 * 统一接口响应对象。
 *
 * <p>当前继承 {@link HashMap} 是为了保持课程项目中常见的链式返回风格，
 * 例如 {@code R.ok().put("data", data)}。后续如果项目规范更严格，
 * 可以再演进为固定字段的响应 DTO。</p>
 */
public class R extends HashMap<String, Object> {

    /**
     * 默认构造成功响应。
     */
    public R() {
        put("code", 0);
        put("msg", "success");
    }

    /**
     * 构造成功响应。
     *
     * @return 成功响应对象
     */
    public static R ok() {
        return new R();
    }

    /**
     * 构造未知异常响应。
     *
     * @return 失败响应对象
     */
    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    /**
     * 构造默认错误码的失败响应。
     *
     * @param msg 错误提示
     * @return 失败响应对象
     */
    public static R error(String msg) {
        return error(500, msg);
    }

    /**
     * 根据错误码和错误提示构造失败响应。
     *
     * @param code 业务错误码
     * @param msg 错误提示
     * @return 失败响应对象
     */
    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     * 根据统一错误码枚举构造失败响应。
     *
     * @param bizCodeEnum 业务错误码枚举
     * @return 失败响应对象
     */
    public static R error(BizCodeEnum bizCodeEnum) {
        return error(bizCodeEnum.getCode(), bizCodeEnum.getMsg());
    }

    /**
     * 覆盖 HashMap 的 put 返回值，支持链式调用。
     *
     * @param key 响应字段名
     * @param value 响应字段值
     * @return 当前响应对象
     */
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
