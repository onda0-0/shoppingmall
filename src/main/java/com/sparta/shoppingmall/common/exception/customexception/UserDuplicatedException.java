package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalDuplicatedException;

public class UserDuplicatedException extends GlobalDuplicatedException {
    public UserDuplicatedException(String message) {
        super(message);
    }
}
