package com.zz.playground.webapp.controller.exception;

/**
 * Created by lugan on 3/17/2017.
 */
public class ThrownException extends RuntimeException{
    public ThrownException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThrownException(String message) {
        super(message);
    }
}
