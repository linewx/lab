package com.zz.callstack.exception;

public class ExceptionCatchClass {
    public void catchException() {
        try {
            new CallExceptionClass().callException();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
