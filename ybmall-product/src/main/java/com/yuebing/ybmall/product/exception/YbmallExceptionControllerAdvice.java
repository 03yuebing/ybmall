package com.yuebing.ybmall.product.exception;

import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.exception.BizException;
import com.yuebing.ybmall.common.utils.R;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * product 服务全局异常处理器。
 *
 * <p>负责把 Controller 层抛出的异常统一转换成 {@link R} 响应，保证前端
 * 接收到稳定的 code/msg/data 结构。</p>
 */
@RestControllerAdvice(basePackages = "com.yuebing.ybmall.product.controller")
public class YbmallExceptionControllerAdvice {

    /**
     * 处理 {@code @Valid} 参数校验失败异常。
     *
     * @param exception 参数校验异常
     * @return 统一错误响应，data 中包含字段名和对应错误提示
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return R.error(BizCodeEnum.VALID_EXCEPTION).put("data", errors);
    }

    /**
     * 处理业务异常。
     *
     * @param exception 业务异常
     * @return 统一错误响应
     */
    @ExceptionHandler(BizException.class)
    public R handleBizException(BizException exception) {
        return R.error(exception.getCode(), exception.getMessage());
    }

    /**
     * 处理未被明确分类的系统异常。
     *
     * @param exception 系统异常
     * @return 统一错误响应
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception exception) {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION);
    }
}
