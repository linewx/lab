package com.zz.lab.springioc;


import com.zz.lab.springioc.entity.Foo;
import com.zz.lab.springioc.entity.FooMethodInterceptor;
import com.zz.lab.springioc.entity.FooRunner;
import com.zz.lab.springioc.entity.FooRunnerInvocationHandler;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

//method interceptor
public class CGLibTest {
    @Test
    public void testCGLib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Foo.class);
        enhancer.setCallback(new FooMethodInterceptor());
        Foo foo = (Foo) enhancer.create();
        foo.setName("jack");
        Assert.assertEquals("jack", foo.getName());
        Assert.assertEquals("hi jack haha", foo.getMessage());

    }
}
