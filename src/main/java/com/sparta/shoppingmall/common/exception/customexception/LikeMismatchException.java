package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalMismatchException;

public class LikeMismatchException extends GlobalMismatchException {
    public LikeMismatchException(String message) {
        super(message);
    }
}
