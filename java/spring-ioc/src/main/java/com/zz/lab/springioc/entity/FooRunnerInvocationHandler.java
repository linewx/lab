package com.zz.lab.springioc.entity;



import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class FooRunnerInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println("before invocation");
        return "foo";
        //return method.invoke(o, objects);
    }
}
