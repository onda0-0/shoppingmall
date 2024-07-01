package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalNotFoundException;

public class OrderNotFoundException extends GlobalNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
