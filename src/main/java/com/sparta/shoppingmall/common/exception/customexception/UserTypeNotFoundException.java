package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalNotFoundException;

public class UserTypeNotFoundException extends GlobalNotFoundException {
    public UserTypeNotFoundException(String message) {
        super(message);
    }
}
