package com.sparta.shoppingmall.common.exception.exceptionhandler;

import com.sparta.shoppingmall.common.base.dto.CommonResponse;
import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalDuplicatedException;
import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalMismatchException;
import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalNotFoundException;
import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalRejectedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sparta.shoppingmall.common.util.ControllerUtil.getBadRequestResponseEntity;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalDuplicatedException.class)
    protected <T> ResponseEntity<CommonResponse<T>> globalMisMatchException(GlobalMismatchException e) {

        return getBadRequestResponseEntity(e);
    }

    @ExceptionHandler(GlobalMismatchException.class)
    protected <T> ResponseEntity<CommonResponse<T>> globalMismatchException(GlobalMismatchException e) {

        return getBadRequestResponseEntity(e);
    }

    @ExceptionHandler(GlobalNotFoundException.class)
    protected <T> ResponseEntity<CommonResponse<T>> globalNotFoundException(GlobalMismatchException e) {

        return getBadRequestResponseEntity(e);
    }

    @ExceptionHandler(GlobalRejectedException.class)
    protected <T> ResponseEntity<CommonResponse<T>> globalRejectedException(GlobalMismatchException e) {

        return getBadRequestResponseEntity(e);
    }

}
