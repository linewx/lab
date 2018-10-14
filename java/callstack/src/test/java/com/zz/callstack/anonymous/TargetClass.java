package com.zz.callstack.anonymous;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class TargetClass implements Runnable{
    public void call(Runnable a) {
        a.run();
    }

    public void invokeLambda() {
        call(() -> {
            System.out.print("lambda function");
        });
    }

    public void invokeStreamLamdba() {
        List<Integer> arrays = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(arrays.stream().reduce((x, y) -> x + y));
    }

    public void inovkeAnonyFunc() {
        this.call(new Runnable() {
            @Override
            public void run() {
                System.out.print("anonymous function");
            }
        });
    }

    public void invokeAnonymousFunction() {
        call(new Runnable() {
            @Override
            public void run() {
                System.out.print("anonymous function");
            }
        });
    }



    public void invokeFunction() {
        call(new TargetClass());
    }

    public static class StaticInnerClass implements Runnable{
        @Override
        public void run() {
            System.out.println("static inner class function");
        }
    }
    public class InnerClass implements Runnable{
        @Override
        public void run() {
            System.out.println("inner class function");
        }
    }

    public void invokeInnerStaticClassFunction() {
        new TargetClass().call(new StaticInnerClass());
    }

    public void invokeInnerClassFunction() {
        new TargetClass().call(new InnerClass());
    }

    public void invokeProxyClass() {

    }

    public void invokeRefectionConstructorClass() {
        try {
            Class<?> clazz = Class.forName("com.zz.callstack.anonymous.TargetClass");
            Constructor<?> constructor = clazz.getConstructor();
            new TargetClass().call((Runnable) constructor.newInstance());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invokeFunctionByReflection() {
        try {
            Class<?> clazz = Class.forName("com.zz.callstack.anonymous.TargetClass");
            Constructor<?> constructor = clazz.getConstructor();
            Method method = clazz.getMethod("run");
            Object obj = constructor.newInstance();
            method.invoke(obj);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class TargetClassProxy implements InvocationHandler {
        private final Runnable runnable;

        TargetClassProxy(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("before invoke");
            //proxy is this, don't invoke proxy
            return method.invoke(runnable, args);
        }
    }

    public void invokeProxyClassFunction() {
        TargetClassProxy  targetClassProxy = new TargetClassProxy(new TargetClass());
        Runnable proxyInstance = (Runnable) Proxy.newProxyInstance(TargetClass.class.getClassLoader(),
                new Class[]{Runnable.class},
                targetClassProxy);
        proxyInstance.run();
    }

    public void invokeCglibClassFunction() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(TargetClass.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy)
                    throws Throwable {
                if(method.getDeclaringClass() != Object.class
                        && method.getReturnType() == String.class) {
                    return "hello cglib!";
                } else {
                    return proxy.invokeSuper(obj, args);
                }
            }
        });
        TargetClass cglibProxy = (TargetClass) enhancer.create();

        cglibProxy.run();

    }



    @Override
    public void run() {
        System.out.println("normal function");
    }

    private void parentRun() {
        System.out.println("run in parent class");
    }

    public void invokeParentFunction() {
        new TargetClass().call(new Runnable() {
            @Override
            public void run() {
                parentRun();
            }
        });
    }
}
