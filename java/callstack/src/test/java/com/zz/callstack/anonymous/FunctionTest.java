package com.zz.callstack.anonymous;

import org.junit.Test;

public class FunctionTest {
    @Test
    public void testLambdaFunction() {
        new TargetClass().invokeLambda();
    }

    @Test
    public void testAnonymousFunction() {
        new TargetClass().invokeAnonymousFunction();
    }

    @Test
    public void testFunction() {
        new TargetClass().invokeFunction();
    }
}
