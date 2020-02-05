package com.zz.lab.springioc.entity;


import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class FooMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (method.getName().equals("getMessage")) {
            String message = (String)methodProxy.invokeSuper(o, objects);
            return message + " haha";

        }else {
            return methodProxy.invokeSuper(o, objects);
        }
    }
}
