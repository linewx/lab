package com.zz.playground.webapp.controller.exception;

/**
 * Created by lugan on 3/17/2017.
 */
public class ServerException extends RuntimeException{
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(String message) {
        super(message);
    }
}
