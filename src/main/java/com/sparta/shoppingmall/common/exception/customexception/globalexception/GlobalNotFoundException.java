package com.sparta.shoppingmall.common.exception.customexception.globalexception;

public class GlobalNotFoundException extends RuntimeException{
    public GlobalNotFoundException (String message) {
        super(message);
    }
}
