package com.linewx.law.instrument.config;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by luganlin on 12/18/16.
 */
public class CGLibProxyTest {
    public static class Target {
        public void Target() {

        }

        public void execute() {
            System.out.println("print target");
        }
    }

    public static class TargetInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            System.out.println("before");
            methodProxy.invokeSuper(o, objects);
            System.out.println("after");
            return null;
        }
    }

    public static Object createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Target.class);
        enhancer.setCallback(new TargetInterceptor());
        return enhancer.create();
    }

    public static void main(String ...argv) {
        Target target = (Target)createProxy();
        target.execute();
    }
}
