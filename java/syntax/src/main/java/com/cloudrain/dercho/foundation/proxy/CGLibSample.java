package com.cloudrain.dercho.foundation.proxy;

/**
 * Created by lugan on 5/3/2016.
 */


import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 使用cglib动态代理
 *
 * @author student
 *
 */
public class CGLibSample implements Callback {
    private Object target;

    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        System.out.println("事物开始");
        proxy.invokeSuper(obj, args);
        System.out.println("事物结束");
        return null;


    }

    public static void main(String ...argv) {

        CGLibSample cglib = new CGLibSample();
        BookStore bookCglib = (BookStore) cglib.getInstance(new BookStore());
        bookCglib.addBook();
    }


}
