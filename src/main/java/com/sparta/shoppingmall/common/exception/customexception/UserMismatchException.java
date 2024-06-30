package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalMismatchException;

public class UserMismatchException extends GlobalMismatchException {
    public UserMismatchException(String message) {
        super(message);
    }
}
