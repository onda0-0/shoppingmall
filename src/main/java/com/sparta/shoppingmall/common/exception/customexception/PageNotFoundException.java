package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalNotFoundException;

public class PageNotFoundException extends GlobalNotFoundException {
    public PageNotFoundException(String message) {
        super(message);
    }
}
