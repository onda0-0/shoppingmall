package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalRejectedException;

public class CartRejectedException extends GlobalRejectedException {
    public CartRejectedException(String message) {
        super(message);
    }
}
