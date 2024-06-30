package com.sparta.shoppingmall.common.exception.customexception;

import com.sparta.shoppingmall.common.exception.customexception.globalexception.GlobalNotFoundException;

public class CommentNotFoundException extends GlobalNotFoundException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
