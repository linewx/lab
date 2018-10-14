package com.zz.callstack.exception;

import org.junit.Test;

public class ExceptionTest {
    @Test
    public void testExceptionCallStack() {
        try {
            new ExceptionCatchClass().catchException();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
