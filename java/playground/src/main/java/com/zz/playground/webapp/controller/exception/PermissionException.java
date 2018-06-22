package com.zz.playground.webapp.controller.exception;

/**
 * Created by lugan on 3/17/2017.
 */
public class PermissionException extends RuntimeException{
    public PermissionException(String message, Throwable cause) {
        super(message, cause);
    }

    public PermissionException(String message) {
        super(message);
    }
}
