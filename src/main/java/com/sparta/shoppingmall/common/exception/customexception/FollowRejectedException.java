package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalRejectedException;

public class FollowRejectedException extends GlobalRejectedException {
    public FollowRejectedException(String message) {
        super(message);
    }
}
