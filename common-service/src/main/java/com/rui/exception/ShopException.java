package com.rui.exception;

//针对于当前shop的各种exception
public class ShopException extends RuntimeException {
    public ShopException(String message) {
        super(message);
    }
}
