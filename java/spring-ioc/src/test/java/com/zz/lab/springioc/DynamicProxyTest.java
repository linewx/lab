package com.zz.lab.springioc;


import com.zz.lab.springioc.entity.FooRunner;
import com.zz.lab.springioc.entity.FooRunnerInvocationHandler;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Proxy;

//invocationhandler
public class DynamicProxyTest {
    @Test
    public void testDynamicProxy() {
        FooRunner fooRunner = (FooRunner) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{FooRunner.class},
                new FooRunnerInvocationHandler()
        );
        Assert.assertEquals("foo", fooRunner.run());
    }
}
