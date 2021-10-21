package com.congcong.jcallpy.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cong
 * <p>
 * created on 2021/4/17 上午11:13
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public R myExceptionHandler(HttpServletRequest request, final Exception e) {
        String method = request.getMethod();
        log.error("方法 {}出现异常：{}",method,e.getMessage(),e);
        return new R(500, method+"方法出现异常", e.getMessage());

    }

}
