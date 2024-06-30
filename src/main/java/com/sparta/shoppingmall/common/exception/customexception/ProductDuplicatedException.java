package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalDuplicatedException;

public class ProductDuplicatedException extends GlobalDuplicatedException {
    public ProductDuplicatedException(String message) {
        super(message);
    }
}
