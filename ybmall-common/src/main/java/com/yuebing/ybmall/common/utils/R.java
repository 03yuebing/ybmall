package com.yuebing.ybmall.common.utils;

import com.yuebing.ybmall.common.exception.BizCodeEnum;

import java.util.HashMap;

public class R extends HashMap<String, Object> {

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R error(BizCodeEnum bizCodeEnum) {
        return error(bizCodeEnum.getCode(), bizCodeEnum.getMsg());
    }
}