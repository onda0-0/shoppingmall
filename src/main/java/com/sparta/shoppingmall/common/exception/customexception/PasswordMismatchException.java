package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalMismatchException;

public class PasswordMismatchException extends GlobalMismatchException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
