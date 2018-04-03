package com.sb.services.utils;

/**
 * Created by Kingsley Kumar on 19/11/2017.
 */
public class CryptoException extends Exception {

    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}