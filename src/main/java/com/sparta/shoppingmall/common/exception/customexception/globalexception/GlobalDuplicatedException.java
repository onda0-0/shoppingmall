package com.sparta.shoppingmall.common.exception.customexception.globalexception;

public class GlobalDuplicatedException  extends RuntimeException{
    public GlobalDuplicatedException(String message) {
        super(message);
    }
}
