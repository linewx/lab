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

    @Test
    public void testStaticInnerClassFunction() {
        new TargetClass().invokeInnerStaticClassFunction();
    }

    @Test
    public void testInnerClassFunction() {
        new TargetClass().invokeInnerClassFunction();
    }

    @Test
    public void testReflectClass() {
        new TargetClass().invokeRefectionConstructorClass();
    }

    @Test
    public void testReflectFunction() {
        new TargetClass().invokeFunctionByReflection();
    }

    @Test
    public void testProxyFunction() {
        new TargetClass().invokeProxyClassFunction();
    }

    @Test
    public void testCglibFunction() {
        new TargetClass().invokeCglibClassFunction();
    }

    @Test
    public void testParentFunction() {
        new TargetClass().invokeParentFunction();
    }

}
