package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalNotFoundException;

public class UserNotFoundException extends GlobalNotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
