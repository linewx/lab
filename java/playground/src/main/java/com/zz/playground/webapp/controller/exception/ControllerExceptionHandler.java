package com.zz.playground.webapp.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by lugan on 3/17/2017.
 */


@ControllerAdvice
class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PermissionException.class)
    @ResponseBody
    public ExceptionResponse handlePermissionException(PermissionException ex) {
        ex.printStackTrace();
        return new ExceptionResponse().withReason(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public ExceptionResponse handleServerException(ServerException ex) {
        ex.printStackTrace();
        return new ExceptionResponse().withReason(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CaughtException.class)
    @ResponseBody
    public ExceptionResponse handleCaughtException(CaughtException ex) {
        ex.printStackTrace();
        return new ExceptionResponse().withReason(ex.getMessage());
    }


    public static class ExceptionResponse {
        private String reason;
        private boolean success;

        public ExceptionResponse() {
            success = false;
        }

        public ExceptionResponse withReason(String reason) {
            this.reason = reason;
            return this;
        }

        // needed for the serialization
        public String getReason() {
            return reason;
        }

        // needed for the serialization
        public boolean isSuccess() {
            return success;
        }
    }

    protected static class SuccessResponse {
        private String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}