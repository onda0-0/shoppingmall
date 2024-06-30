package com.sparta.shoppingmall.common.exception.exceptionhandler;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sparta.shoppingmall.common.util.ControllerUtil.getFieldErrorResponseEntity;

@Slf4j
@RestControllerAdvice
public class ValidExceptionHandler {

    /**
     * 유효성 검사 에러 체크
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<CommonResponse> validException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : " + e.getMessage());

        return getFieldErrorResponseEntity(e.getBindingResult(), e.getMessage());
    }

}
