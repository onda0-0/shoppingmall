package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalNotFoundException;

public class ProductNotFoundException extends GlobalNotFoundException {
    public ProductNotFoundException (String message) {
        super(message);
    }
}
