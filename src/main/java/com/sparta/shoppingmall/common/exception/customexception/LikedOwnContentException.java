package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalMismatchException;

public class LikedOwnContentException extends GlobalMismatchException {
    public LikedOwnContentException(String message) {
        super(message);
    }
}
