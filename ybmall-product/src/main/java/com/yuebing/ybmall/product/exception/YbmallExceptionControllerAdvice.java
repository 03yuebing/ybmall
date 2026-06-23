package com.yuebing.ybmall.product.exception;

import com.yuebing.ybmall.common.exception.BizCodeEnum;
import com.yuebing.ybmall.common.exception.BizException;
import com.yuebing.ybmall.common.utils.R;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.yuebing.ybmall.product.controller")
public class YbmallExceptionControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return R.error(BizCodeEnum.VALID_EXCEPTION).put("data", errors);
    }

    @ExceptionHandler(BizException.class)
    public R handleBizException(BizException exception) {
        return R.error(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception exception) {
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION);
    }

}
