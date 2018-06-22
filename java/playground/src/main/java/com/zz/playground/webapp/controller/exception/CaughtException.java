package com.zz.playground.webapp.controller.exception;

/**
 * Created by lugan on 3/17/2017.
 */
public class CaughtException extends RuntimeException{
    public CaughtException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaughtException(String message) {
        super(message);
    }
}
